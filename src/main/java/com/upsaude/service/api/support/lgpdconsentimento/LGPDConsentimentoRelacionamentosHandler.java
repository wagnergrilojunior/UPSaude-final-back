package com.upsaude.service.api.support.lgpdconsentimento;

import com.upsaude.api.request.sistema.lgpd.LGPDConsentimentoRequest;
import com.upsaude.entity.sistema.lgpd.LGPDConsentimento;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.service.api.support.paciente.PacienteTenantEnforcer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LGPDConsentimentoRelacionamentosHandler {

    private final PacienteTenantEnforcer pacienteTenantEnforcer;

    public void resolver(LGPDConsentimento entity, LGPDConsentimentoRequest request, UUID tenantId, Tenant tenant) {
        if (request == null) return;

        entity.setTenant(Objects.requireNonNull(tenant, "tenant é obrigatório"));

        UUID pacienteId = Objects.requireNonNull(request.getPaciente(), "paciente");
        Paciente paciente = pacienteTenantEnforcer.validarAcesso(pacienteId, tenantId);
        entity.setPaciente(paciente);

    }
}
