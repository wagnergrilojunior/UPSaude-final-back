package com.upsaude.service.impl.api.financeiro;

import com.upsaude.api.request.financeiro.EstornoFinanceiroRequest;
import com.upsaude.api.request.financeiro.ReservaOrcamentariaAssistencialRequest;
import com.upsaude.entity.agendamento.Agendamento;
import com.upsaude.entity.clinica.atendimento.Atendimento;
import com.upsaude.entity.financeiro.ReservaOrcamentariaAssistencial;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.agendamento.AgendamentoRepository;
import com.upsaude.repository.clinica.atendimento.AtendimentoRepository;
import com.upsaude.repository.financeiro.ReservaOrcamentariaAssistencialRepository;
import com.upsaude.service.api.financeiro.EstornoFinanceiroService;
import com.upsaude.service.api.financeiro.FinanceiroIntegrationService;
import com.upsaude.service.api.financeiro.ReservaOrcamentariaAssistencialService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FinanceiroIntegrationServiceImpl implements FinanceiroIntegrationService {

    private final AgendamentoRepository agendamentoRepository;
    private final AtendimentoRepository atendimentoRepository;
    private final ReservaOrcamentariaAssistencialRepository reservaRepository;
    private final ReservaOrcamentariaAssistencialService reservaService;
    private final EstornoFinanceiroService estornoService;

    @Override
    @Transactional
    public void reservarOrcamento(UUID agendamentoId) {
        if (agendamentoId == null) throw new BadRequestException("agendamentoId é obrigatório");

        Agendamento agendamento = agendamentoRepository.findById(agendamentoId)
                .orElseThrow(() -> new NotFoundException("Agendamento não encontrado com ID: " + agendamentoId));

        UUID tenantId = agendamento.getTenant() != null ? agendamento.getTenant().getId() : null;
        if (tenantId != null) {
            Optional<ReservaOrcamentariaAssistencial> existingOpt = reservaRepository.findByAgendamento(agendamentoId, tenantId);
            if (existingOpt.isPresent()) {
                log.info("Reserva já existe para agendamento. ReservaID: {}, agendamentoId: {}", existingOpt.get().getId(), agendamentoId);
                if (!"RESERVADO".equalsIgnoreCase(agendamento.getStatusFinanceiro())) {
                    agendamento.setStatusFinanceiro("RESERVADO");
                    agendamentoRepository.save(agendamento);
                }
                return;
            }
        }

        if (agendamento.getCompetenciaFinanceira() == null) {
            log.info("Agendamento sem competência financeira vinculada; não é possível reservar orçamento. agendamentoId: {}", agendamentoId);
            return;
        }

        BigDecimal valor = agendamento.getValorEstimadoTotal() != null ? agendamento.getValorEstimadoTotal() : BigDecimal.ZERO;
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            log.info("Agendamento sem valor estimado para reserva. agendamentoId: {}", agendamentoId);
            return;
        }

        ReservaOrcamentariaAssistencialRequest req = ReservaOrcamentariaAssistencialRequest.builder()
                .competencia(agendamento.getCompetenciaFinanceira().getId())
                .agendamento(agendamentoId)
                .valorReservadoTotal(valor)
                .status("ATIVA")
                .build();

        reservaService.criar(req);
        agendamento.setStatusFinanceiro("RESERVADO");
        agendamentoRepository.save(agendamento);
    }

    @Override
    @Transactional
    public void estornarReserva(UUID agendamentoId, String motivo) {
        if (agendamentoId == null) throw new BadRequestException("agendamentoId é obrigatório");

        Agendamento agendamento = agendamentoRepository.findById(agendamentoId)
                .orElseThrow(() -> new NotFoundException("Agendamento não encontrado com ID: " + agendamentoId));
        UUID tenantId = agendamento.getTenant() != null ? agendamento.getTenant().getId() : null;
        if (tenantId == null) throw new BadRequestException("Tenant do agendamento é obrigatório");

        ReservaOrcamentariaAssistencial reserva = reservaRepository.findByAgendamento(agendamentoId, tenantId)
                .orElse(null);
        if (reserva == null) {
            log.info("Nenhuma reserva encontrada para estorno. agendamentoId: {}", agendamentoId);
            agendamento.setStatusFinanceiro("ESTORNADO");
            agendamentoRepository.save(agendamento);
            return;
        }

        if ("LIBERADA".equalsIgnoreCase(reserva.getStatus())) {
            log.info("Reserva já liberada; ignorando estorno duplicado. reservaId: {}, agendamentoId: {}", reserva.getId(), agendamentoId);
            agendamento.setStatusFinanceiro("ESTORNADO");
            agendamentoRepository.save(agendamento);
            return;
        }

        BigDecimal valor = reserva.getValorReservadoTotal() != null ? reserva.getValorReservadoTotal() : BigDecimal.ZERO;
        if (valor.compareTo(BigDecimal.ZERO) > 0) {
            EstornoFinanceiroRequest estornoReq = EstornoFinanceiroRequest.builder()
                    .competencia(reserva.getCompetencia().getId())
                    .agendamento(agendamentoId)
                    .paciente(agendamento.getPaciente() != null ? agendamento.getPaciente().getId() : null)
                    .motivo(motivo != null ? motivo : "CANCELAMENTO")
                    .valorEstornado(valor)
                    .dataEstorno(OffsetDateTime.now())
                    .observacoes("Estorno automático de reserva orçamentária")
                    .build();

            if (estornoReq.getPaciente() != null) {
                estornoService.criar(estornoReq);
            }
        }

        reserva.setStatus("LIBERADA");
        reservaRepository.save(reserva);
        agendamento.setStatusFinanceiro("ESTORNADO");
        agendamentoRepository.save(agendamento);
    }

    @Override
    @Transactional
    public void consumirReserva(UUID atendimentoId) {
        if (atendimentoId == null) throw new BadRequestException("atendimentoId é obrigatório");
        Atendimento atendimento = atendimentoRepository.findById(atendimentoId)
                .orElseThrow(() -> new NotFoundException("Atendimento não encontrado com ID: " + atendimentoId));

        UUID tenantId = atendimento.getTenant() != null ? atendimento.getTenant().getId() : null;
        if (tenantId == null) {
            log.warn("Atendimento sem tenant; não é possível consumir reserva. atendimentoId: {}", atendimentoId);
            return;
        }

        Optional<Agendamento> agendamentoOpt = agendamentoRepository.findByAtendimento(atendimentoId, tenantId);
        if (agendamentoOpt.isEmpty()) {
            log.info("Nenhum agendamento vinculado ao atendimento; nada a consumir. atendimentoId: {}", atendimentoId);
            return;
        }

        Agendamento agendamento = agendamentoOpt.get();
        ReservaOrcamentariaAssistencial reserva = reservaRepository.findByAgendamento(agendamento.getId(), tenantId).orElse(null);
        if (reserva == null) {
            log.info("Nenhuma reserva encontrada para consumo. agendamentoId: {}, atendimentoId: {}", agendamento.getId(), atendimentoId);
            return;
        }

        if ("CONSUMIDA".equalsIgnoreCase(reserva.getStatus())) {
            if (!"CONSUMIDO".equalsIgnoreCase(agendamento.getStatusFinanceiro())) {
                agendamento.setStatusFinanceiro("CONSUMIDO");
                agendamentoRepository.save(agendamento);
            }
            return;
        }

        reserva.setStatus("CONSUMIDA");
        reservaRepository.save(reserva);
        agendamento.setStatusFinanceiro("CONSUMIDO");
        agendamentoRepository.save(agendamento);
    }

    @Override
    @Transactional
    public void estornarConsumo(UUID atendimentoId, String motivo) {
        if (atendimentoId == null) throw new BadRequestException("atendimentoId é obrigatório");
        Atendimento atendimento = atendimentoRepository.findById(atendimentoId)
                .orElseThrow(() -> new NotFoundException("Atendimento não encontrado com ID: " + atendimentoId));

        UUID tenantId = atendimento.getTenant() != null ? atendimento.getTenant().getId() : null;
        if (tenantId == null) {
            log.warn("Atendimento sem tenant; não é possível estornar consumo. atendimentoId: {}", atendimentoId);
            return;
        }

        Optional<Agendamento> agendamentoOpt = agendamentoRepository.findByAtendimento(atendimentoId, tenantId);
        if (agendamentoOpt.isEmpty()) {
            log.info("Nenhum agendamento vinculado ao atendimento; nada a estornar. atendimentoId: {}", atendimentoId);
            return;
        }

        estornarReserva(agendamentoOpt.get().getId(), motivo != null ? motivo : "CANCELAMENTO_ATENDIMENTO");
    }

    @Override
    @Transactional
    public void fecharCompetencia(UUID competenciaFinanceiraId) {
        if (competenciaFinanceiraId == null) throw new BadRequestException("competenciaFinanceiraId é obrigatório");
        log.warn("fecharCompetencia chamado sem tenantId e usuarioId. Use CompetenciaFechamentoService diretamente.");
    }
}

