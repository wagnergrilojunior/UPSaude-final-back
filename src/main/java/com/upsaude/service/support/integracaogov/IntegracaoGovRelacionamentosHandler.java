package com.upsaude.service.support.integracaogov;

import com.upsaude.api.request.IntegracaoGovRequest;
import com.upsaude.entity.IntegracaoGov;
import com.upsaude.entity.Paciente;
import com.upsaude.entity.Tenant;
import com.upsaude.service.support.paciente.PacienteTenantEnforcer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

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

        // estabelecimento: manter comportamento atual (pode ser null)
    }
}
