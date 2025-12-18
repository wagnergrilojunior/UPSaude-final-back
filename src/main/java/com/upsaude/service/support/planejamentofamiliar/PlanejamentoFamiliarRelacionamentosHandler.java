package com.upsaude.service.support.planejamentofamiliar;

import com.upsaude.api.request.planejamento.PlanejamentoFamiliarRequest;
import com.upsaude.entity.planejamento.PlanejamentoFamiliar;
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
public class PlanejamentoFamiliarRelacionamentosHandler {

    private final PacienteTenantEnforcer pacienteTenantEnforcer;
    private final ProfissionaisSaudeTenantEnforcer profissionaisSaudeTenantEnforcer;
    private final EquipeSaudeTenantEnforcer equipeSaudeTenantEnforcer;

    public PlanejamentoFamiliar processarRelacionamentos(PlanejamentoFamiliarRequest request,
                                                        PlanejamentoFamiliar entity,
                                                        UUID tenantId,
                                                        Tenant tenant) {
        if (tenantId == null || tenant == null || tenant.getId() == null || !tenantId.equals(tenant.getId())) {
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado");
        }

        entity.setTenant(tenant);

        entity.setPaciente(pacienteTenantEnforcer.validarAcesso(request.getPaciente(), tenantId));

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

