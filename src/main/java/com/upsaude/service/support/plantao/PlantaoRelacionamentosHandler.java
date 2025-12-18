package com.upsaude.service.support.plantao;

import com.upsaude.api.request.equipe.PlantaoRequest;
import com.upsaude.entity.equipe.Plantao;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.service.support.estabelecimentos.EstabelecimentosTenantEnforcer;
import com.upsaude.service.support.medico.MedicoTenantEnforcer;
import com.upsaude.service.support.profissionaissaude.ProfissionaisSaudeTenantEnforcer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PlantaoRelacionamentosHandler {

    private final ProfissionaisSaudeTenantEnforcer profissionaisSaudeTenantEnforcer;
    private final MedicoTenantEnforcer medicoTenantEnforcer;
    private final EstabelecimentosTenantEnforcer estabelecimentosTenantEnforcer;

    public Plantao processarRelacionamentos(PlantaoRequest request, Plantao entity, UUID tenantId, Tenant tenant) {
        if (tenantId == null || tenant == null || tenant.getId() == null || !tenantId.equals(tenant.getId())) {
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado");
        }

        entity.setTenant(tenant);

        entity.setProfissional(profissionaisSaudeTenantEnforcer.validarAcesso(request.getProfissional(), tenantId));

        if (request.getMedico() != null) {
            entity.setMedico(medicoTenantEnforcer.validarAcesso(request.getMedico(), tenantId));
        } else {
            entity.setMedico(null);
        }

        entity.setEstabelecimento(estabelecimentosTenantEnforcer.validarAcesso(request.getEstabelecimento(), tenantId));

        return entity;
    }
}

