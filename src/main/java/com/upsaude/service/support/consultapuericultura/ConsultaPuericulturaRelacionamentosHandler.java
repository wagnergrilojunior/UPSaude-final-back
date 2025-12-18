package com.upsaude.service.support.consultapuericultura;

import com.upsaude.api.request.clinica.atendimento.ConsultaPuericulturaRequest;
import com.upsaude.entity.clinica.atendimento.ConsultaPuericultura;
import com.upsaude.entity.saude_publica.puericultura.Puericultura;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.service.support.profissionaissaude.ProfissionaisSaudeTenantEnforcer;
import com.upsaude.service.support.puericultura.PuericulturaTenantEnforcer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConsultaPuericulturaRelacionamentosHandler {

    private final PuericulturaTenantEnforcer puericulturaTenantEnforcer;
    private final ProfissionaisSaudeTenantEnforcer profissionaisSaudeTenantEnforcer;

    public ConsultaPuericultura processarRelacionamentos(ConsultaPuericulturaRequest request,
                                                         ConsultaPuericultura entity,
                                                         UUID tenantId,
                                                         Tenant tenant) {
        if (tenantId == null || tenant == null || tenant.getId() == null || !tenantId.equals(tenant.getId())) {
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado");
        }

        entity.setTenant(tenant);

        Puericultura puericultura = puericulturaTenantEnforcer.validarAcesso(request.getPuericultura(), tenantId);
        entity.setPuericultura(puericultura);

        entity.setProfissional(profissionaisSaudeTenantEnforcer.validarAcesso(request.getProfissional(), tenantId));

        if (puericultura.getEstabelecimento() != null) {
            entity.setEstabelecimento(puericultura.getEstabelecimento());
        }

        return entity;
    }
}
