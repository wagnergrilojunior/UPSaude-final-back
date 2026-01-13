package com.upsaude.service.api.support.financeiro.reserva;

import com.upsaude.api.request.financeiro.ReservaOrcamentariaAssistencialRequest;
import com.upsaude.entity.financeiro.OrcamentoCompetencia;
import com.upsaude.entity.financeiro.ReservaOrcamentariaAssistencial;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.mapper.financeiro.ReservaOrcamentariaAssistencialMapper;
import com.upsaude.repository.financeiro.OrcamentoCompetenciaRepository;
import com.upsaude.repository.financeiro.ReservaOrcamentariaAssistencialRepository;
import com.upsaude.service.api.support.financeiro.orcamento.OrcamentoCompetenciaDomainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservaOrcamentariaAssistencialUpdater {

    private final ReservaOrcamentariaAssistencialRepository repository;
    private final OrcamentoCompetenciaRepository orcamentoRepository;
    private final OrcamentoCompetenciaDomainService orcamentoDomainService;
    private final ReservaOrcamentariaAssistencialMapper mapper;
    private final ReservaOrcamentariaAssistencialTenantEnforcer tenantEnforcer;
    private final ReservaOrcamentariaAssistencialRelacionamentosHandler relacionamentosHandler;
    private final ReservaOrcamentariaAssistencialDomainService domainService;

    public ReservaOrcamentariaAssistencial atualizar(UUID id, ReservaOrcamentariaAssistencialRequest request, UUID tenantId, Tenant tenant) {
        ReservaOrcamentariaAssistencial entity = tenantEnforcer.validarAcesso(id, tenantId);
        BigDecimal valorAnterior = entity.getValorReservadoTotal() != null ? entity.getValorReservadoTotal() : BigDecimal.ZERO;

        mapper.updateFromRequest(request, entity);
        relacionamentosHandler.resolver(entity, request, tenantId, tenant);
        domainService.aplicarDefaults(entity);

        OrcamentoCompetencia orcamento = orcamentoRepository.findByTenantAndCompetencia(tenantId, request.getCompetencia())
                .orElseThrow(() -> new com.upsaude.exception.BadRequestException("Orçamento não encontrado para esta competência"));

        BigDecimal valorNovo = entity.getValorReservadoTotal() != null ? entity.getValorReservadoTotal() : BigDecimal.ZERO;
        BigDecimal delta = valorNovo.subtract(valorAnterior);
        if (delta.compareTo(BigDecimal.ZERO) != 0) {
            domainService.validarSaldoDisponivel(orcamento, delta.compareTo(BigDecimal.ZERO) > 0 ? delta : BigDecimal.ZERO);
            BigDecimal reservas = orcamento.getReservasAtivas() != null ? orcamento.getReservasAtivas() : BigDecimal.ZERO;
            orcamento.setReservasAtivas(reservas.add(delta));
            orcamentoDomainService.aplicarDefaults(orcamento);
            orcamentoRepository.save(orcamento);
        }

        ReservaOrcamentariaAssistencial saved = repository.save(Objects.requireNonNull(entity));
        log.info("Reserva orçamentária atualizada com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}

