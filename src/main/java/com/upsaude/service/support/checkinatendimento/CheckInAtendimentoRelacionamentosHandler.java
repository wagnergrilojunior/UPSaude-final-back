package com.upsaude.service.support.checkinatendimento;

import com.upsaude.api.request.clinica.atendimento.CheckInAtendimentoRequest;
import com.upsaude.entity.agendamento.Agendamento;
import com.upsaude.entity.clinica.atendimento.Atendimento;
import com.upsaude.entity.clinica.atendimento.CheckInAtendimento;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.service.support.atendimento.AtendimentoTenantEnforcer;
import com.upsaude.repository.agendamento.AgendamentoRepository;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.support.paciente.PacienteTenantEnforcer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CheckInAtendimentoRelacionamentosHandler {

    private final AgendamentoRepository agendamentoRepository;
    private final PacienteTenantEnforcer pacienteTenantEnforcer;
    private final AtendimentoTenantEnforcer atendimentoTenantEnforcer;

    public void resolver(CheckInAtendimento entity, CheckInAtendimentoRequest request, UUID tenantId, Tenant tenant) {
        if (request == null) return;

        entity.setTenant(Objects.requireNonNull(tenant, "tenant é obrigatório"));

        Agendamento agendamento = null;
        if (request.getAgendamento() != null) {
            agendamento = agendamentoRepository.findByIdAndTenant(request.getAgendamento(), tenantId)
                    .orElseThrow(() -> new NotFoundException("Agendamento não encontrado com ID: " + request.getAgendamento()));
            entity.setAgendamento(agendamento);
        } else {
            entity.setAgendamento(null);
        }

        Paciente paciente = pacienteTenantEnforcer.validarAcesso(request.getPaciente(), tenantId);
        entity.setPaciente(paciente);

        if (request.getAtendimento() != null) {
            Atendimento atendimento = atendimentoTenantEnforcer.validarAcesso(request.getAtendimento(), tenantId);
            entity.setAtendimento(atendimento);
        } else {
            entity.setAtendimento(null);
        }

        if (agendamento != null && agendamento.getEstabelecimento() != null) {
            entity.setEstabelecimento(agendamento.getEstabelecimento());
        } else if (entity.getAtendimento() != null && entity.getAtendimento().getEstabelecimento() != null) {
            entity.setEstabelecimento(entity.getAtendimento().getEstabelecimento());
        } else {
            entity.setEstabelecimento(null);
        }
    }
}
