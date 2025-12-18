package com.upsaude.service.support.lgpdconsentimento;

import com.upsaude.api.request.sistema.LGPDConsentimentoRequest;
import com.upsaude.entity.sistema.LGPDConsentimento;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.service.support.paciente.PacienteTenantEnforcer;
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

        // estabelecimento: manter comportamento atual (pode ser null)
    }
}
