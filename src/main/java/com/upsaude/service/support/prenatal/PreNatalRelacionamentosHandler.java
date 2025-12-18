package com.upsaude.service.support.prenatal;

import com.upsaude.api.request.planejamento.PreNatalRequest;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.entity.planejamento.PreNatal;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.service.support.equipesaude.EquipeSaudeTenantEnforcer;
import com.upsaude.service.support.paciente.PacienteTenantEnforcer;
import com.upsaude.service.support.profissionaissaude.ProfissionaisSaudeTenantEnforcer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PreNatalRelacionamentosHandler {

    private final PacienteTenantEnforcer pacienteTenantEnforcer;
    private final ProfissionaisSaudeTenantEnforcer profissionaisSaudeTenantEnforcer;
    private final EquipeSaudeTenantEnforcer equipeSaudeTenantEnforcer;

    public PreNatal processarRelacionamentos(PreNatalRequest request, PreNatal entity, UUID tenantId, Tenant tenant) {
        if (tenantId == null || tenant == null || tenant.getId() == null || !tenantId.equals(tenant.getId())) {
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado");
        }

        entity.setTenant(tenant);

        Paciente paciente = pacienteTenantEnforcer.validarAcesso(request.getPaciente(), tenantId);
        entity.setPaciente(paciente);

        if (request.getProfissionalResponsavel() != null) {
            entity.setProfissionalResponsavel(profissionaisSaudeTenantEnforcer.validarAcesso(request.getProfissionalResponsavel(), tenantId));
        } else {
            entity.setProfissionalResponsavel(null);
        }

        if (request.getEquipeSaude() != null) {
            entity.setEquipeSaude(equipeSaudeTenantEnforcer.validarAcesso(request.getEquipeSaude(), tenantId));
        } else {
            entity.setEquipeSaude(null);
        }

        return entity;
    }
}

