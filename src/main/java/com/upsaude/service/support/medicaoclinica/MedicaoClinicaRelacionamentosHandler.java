package com.upsaude.service.support.medicaoclinica;

import com.upsaude.api.request.medicao.MedicaoClinicaRequest;
import com.upsaude.entity.medicao.MedicaoClinica;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.service.support.paciente.PacienteTenantEnforcer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MedicaoClinicaRelacionamentosHandler {

    private final PacienteTenantEnforcer pacienteTenantEnforcer;

    public void resolver(MedicaoClinica entity, MedicaoClinicaRequest request, UUID tenantId, Tenant tenant) {
        if (request == null) return;

        entity.setTenant(Objects.requireNonNull(tenant, "tenant é obrigatório"));

        Paciente paciente = pacienteTenantEnforcer.validarAcesso(Objects.requireNonNull(request.getPaciente(), "paciente"), tenantId);
        entity.setPaciente(paciente);

        // estabelecimento: manter comportamento atual (pode ser null)
    }
}
