package com.upsaude.service.support.medicaoclinica;

import com.upsaude.api.request.profissional.medicao.MedicaoClinicaRequest;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.entity.profissional.medicao.MedicaoClinica;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.service.support.paciente.PacienteTenantEnforcer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MedicaoClinicaRelacionamentosHandler {

    private final PacienteTenantEnforcer pacienteTenantEnforcer;

    public void resolver(MedicaoClinica entity, MedicaoClinicaRequest request, UUID tenantId, Tenant tenant) {
        if (request == null) return;

        if (tenantId == null || tenant == null || tenant.getId() == null || !tenantId.equals(tenant.getId())) {
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado");
        }

        entity.setTenant(tenant);

        if (request.getPaciente() != null) {
            Paciente paciente = pacienteTenantEnforcer.validarAcesso(request.getPaciente(), tenantId);
            entity.setPaciente(paciente);
        }
    }
}
