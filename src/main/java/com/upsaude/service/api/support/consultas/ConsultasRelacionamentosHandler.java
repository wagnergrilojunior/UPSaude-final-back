package com.upsaude.service.api.support.consultas;

import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.api.request.clinica.atendimento.ConsultaRequest;
import com.upsaude.entity.clinica.atendimento.Consulta;
import com.upsaude.entity.clinica.atendimento.Atendimento;
import com.upsaude.entity.profissional.Medicos;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.clinica.atendimento.AtendimentoRepository;
import com.upsaude.service.api.support.medico.MedicoTenantEnforcer;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConsultasRelacionamentosHandler {

    private final MedicoTenantEnforcer medicoTenantEnforcer;
    private final AtendimentoRepository atendimentoRepository;

    public void resolver(Consulta entity, ConsultaRequest request, UUID tenantId, Tenant tenant) {
        if (request == null) return;

        entity.setTenant(Objects.requireNonNull(tenant, "tenant é obrigatório"));

        UUID atendimentoId = Objects.requireNonNull(request.getAtendimento(), "atendimento é obrigatório");
        Atendimento atendimento = atendimentoRepository.findByIdAndTenant(atendimentoId, tenantId)
            .orElseThrow(() -> new NotFoundException("Atendimento não encontrado com ID: " + atendimentoId));
        entity.setAtendimento(atendimento);

        UUID medicoId = Objects.requireNonNull(request.getMedico(), "médico é obrigatório");
        Medicos medico = medicoTenantEnforcer.validarAcesso(medicoId, tenantId);
        entity.setMedico(medico);
    }
}
