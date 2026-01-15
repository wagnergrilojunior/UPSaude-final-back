package com.upsaude.service.api.support.financeiro.estorno;

import com.upsaude.api.request.financeiro.EstornoFinanceiroRequest;
import com.upsaude.entity.agendamento.Agendamento;
import com.upsaude.entity.clinica.atendimento.Atendimento;
import com.upsaude.entity.clinica.atendimento.AtendimentoProcedimento;
import com.upsaude.entity.financeiro.CompetenciaFinanceira;
import com.upsaude.entity.financeiro.EstornoFinanceiro;
import com.upsaude.entity.financeiro.GuiaAtendimentoAmbulatorial;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.repository.agendamento.AgendamentoRepository;
import com.upsaude.repository.clinica.atendimento.AtendimentoProcedimentoRepository;
import com.upsaude.repository.clinica.atendimento.AtendimentoRepository;
import com.upsaude.repository.financeiro.CompetenciaFinanceiraRepository;
import com.upsaude.repository.financeiro.GuiaAtendimentoAmbulatorialRepository;
import com.upsaude.repository.paciente.PacienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EstornoFinanceiroRelacionamentosHandler {

    private final CompetenciaFinanceiraRepository competenciaFinanceiraRepository;
    private final AgendamentoRepository agendamentoRepository;
    private final AtendimentoRepository atendimentoRepository;
    private final GuiaAtendimentoAmbulatorialRepository guiaRepository;
    private final PacienteRepository pacienteRepository;
    private final AtendimentoProcedimentoRepository atendimentoProcedimentoRepository;

    public void resolver(EstornoFinanceiro entity, EstornoFinanceiroRequest request, UUID tenantId, Tenant tenant) {
        if (request == null) return;
        entity.setTenant(Objects.requireNonNull(tenant, "tenant é obrigatório"));

        CompetenciaFinanceira competencia = competenciaFinanceiraRepository.findById(request.getCompetencia())
                .orElseThrow(() -> new BadRequestException("Competência financeira não encontrada com ID: " + request.getCompetencia()));
        entity.setCompetencia(competencia);

        if (request.getAgendamento() != null) {
            Agendamento ag = agendamentoRepository.findByIdAndTenant(request.getAgendamento(), tenantId)
                    .orElseThrow(() -> new BadRequestException("Agendamento não encontrado com ID: " + request.getAgendamento()));
            entity.setAgendamento(ag);
        } else {
            entity.setAgendamento(null);
        }

        if (request.getAtendimento() != null) {
            Atendimento at = atendimentoRepository.findByIdAndTenant(request.getAtendimento(), tenantId)
                    .orElseThrow(() -> new BadRequestException("Atendimento não encontrado com ID: " + request.getAtendimento()));
            entity.setAtendimento(at);
        } else {
            entity.setAtendimento(null);
        }

        if (request.getGuiaAmbulatorial() != null) {
            GuiaAtendimentoAmbulatorial guia = guiaRepository.findByIdAndTenant(request.getGuiaAmbulatorial(), tenantId)
                    .orElseThrow(() -> new BadRequestException("Guia ambulatorial não encontrada com ID: " + request.getGuiaAmbulatorial()));
            entity.setGuiaAmbulatorial(guia);
        } else {
            entity.setGuiaAmbulatorial(null);
        }

        // Paciente não tem tenant_id (estende BaseEntityWithoutTenant)
        Paciente paciente = pacienteRepository.findById(request.getPaciente())
                .orElseThrow(() -> new BadRequestException("Paciente não encontrado com ID: " + request.getPaciente()));
        entity.setPaciente(paciente);

        if (request.getAtendimentoProcedimento() != null) {
            AtendimentoProcedimento ap = atendimentoProcedimentoRepository.findByIdAndTenant(request.getAtendimentoProcedimento(), tenantId)
                    .orElseThrow(() -> new BadRequestException("AtendimentoProcedimento não encontrado com ID: " + request.getAtendimentoProcedimento()));
            entity.setAtendimentoProcedimento(ap);
        } else {
            entity.setAtendimentoProcedimento(null);
        }
    }
}

