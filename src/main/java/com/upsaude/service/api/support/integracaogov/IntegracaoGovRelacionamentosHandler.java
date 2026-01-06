package com.upsaude.service.api.support.integracaogov;

import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.api.request.sistema.integracao.IntegracaoGovRequest;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.entity.sistema.integracao.IntegracaoGov;
import com.upsaude.service.api.support.paciente.PacienteTenantEnforcer;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IntegracaoGovRelacionamentosHandler {

    private final PacienteTenantEnforcer pacienteTenantEnforcer;

    public void resolver(IntegracaoGov entity, IntegracaoGovRequest request, UUID tenantId, Tenant tenant) {
        if (request == null) return;

        entity.setTenant(Objects.requireNonNull(tenant, "tenant é obrigatório"));

        UUID pacienteId = Objects.requireNonNull(request.getPaciente(), "paciente");
        Paciente paciente = pacienteTenantEnforcer.validarAcesso(pacienteId, tenantId);
        entity.setPaciente(paciente);

    }
}
