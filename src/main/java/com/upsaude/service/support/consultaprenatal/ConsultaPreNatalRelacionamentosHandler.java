package com.upsaude.service.support.consultaprenatal;

import com.upsaude.api.request.clinica.atendimento.ConsultaPreNatalRequest;
import com.upsaude.entity.clinica.atendimento.ConsultaPreNatal;
import com.upsaude.entity.saude_publica.planejamento.PreNatal;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.service.support.prenatal.PreNatalTenantEnforcer;
import com.upsaude.service.support.profissionaissaude.ProfissionaisSaudeTenantEnforcer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConsultaPreNatalRelacionamentosHandler {

    private final PreNatalTenantEnforcer preNatalTenantEnforcer;
    private final ProfissionaisSaudeTenantEnforcer profissionaisSaudeTenantEnforcer;

    public ConsultaPreNatal processarRelacionamentos(ConsultaPreNatalRequest request,
                                                    ConsultaPreNatal entity,
                                                    UUID tenantId,
                                                    Tenant tenant) {
        if (tenantId == null || tenant == null || tenant.getId() == null || !tenantId.equals(tenant.getId())) {
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado");
        }

        entity.setTenant(tenant);

        PreNatal preNatal = preNatalTenantEnforcer.validarAcesso(request.getPreNatal(), tenantId);
        entity.setPreNatal(preNatal);

        entity.setProfissional(profissionaisSaudeTenantEnforcer.validarAcesso(request.getProfissional(), tenantId));

        if (preNatal.getEstabelecimento() != null) {
            entity.setEstabelecimento(preNatal.getEstabelecimento());
        }

        return entity;
    }
}

