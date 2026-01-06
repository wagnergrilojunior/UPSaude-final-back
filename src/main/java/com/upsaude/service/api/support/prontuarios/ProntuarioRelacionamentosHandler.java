package com.upsaude.service.api.support.prontuarios;

import com.upsaude.api.request.clinica.prontuario.ProntuarioRequest;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.entity.clinica.prontuario.Prontuario;
import com.upsaude.entity.profissional.ProfissionaisSaude;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.service.api.support.paciente.PacienteTenantEnforcer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProntuarioRelacionamentosHandler {

    private final PacienteTenantEnforcer pacienteTenantEnforcer;

    public void resolver(Prontuario entity, ProntuarioRequest request, UUID tenantId, Tenant tenant) {
        if (request == null) return;

        entity.setTenant(Objects.requireNonNull(tenant, "tenant é obrigatório"));

        if (request.getPaciente() != null) {
            Paciente paciente = pacienteTenantEnforcer.validarAcesso(request.getPaciente(), tenantId);
            entity.setPaciente(paciente);
        }

        if (request.getProfissionalCriador() != null) {
            // Profissional será resolvido pelo mapper ou service
        }
    }
}

