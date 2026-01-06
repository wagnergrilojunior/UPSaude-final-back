package com.upsaude.service.api.support.prontuarios;

import com.upsaude.api.request.clinica.prontuario.ProntuariosRequest;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.entity.clinica.prontuario.Prontuarios;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.service.api.support.paciente.PacienteTenantEnforcer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProntuariosRelacionamentosHandler {

    private final PacienteTenantEnforcer pacienteTenantEnforcer;

    public void resolver(Prontuarios entity, ProntuariosRequest request, UUID tenantId, Tenant tenant) {
        if (request == null) return;

        if (request.getPaciente() != null) {
            Paciente paciente = pacienteTenantEnforcer.validarAcesso(request.getPaciente(), tenantId);
            entity.setPaciente(paciente);
        }

        entity.setTenant(Objects.requireNonNull(tenant, "tenant é obrigatório"));
    }
}
