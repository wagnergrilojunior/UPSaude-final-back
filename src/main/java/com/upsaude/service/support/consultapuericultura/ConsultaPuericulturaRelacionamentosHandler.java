package com.upsaude.service.support.consultapuericultura;

import com.upsaude.api.request.atendimento.ConsultaPuericulturaRequest;
import com.upsaude.entity.atendimento.ConsultaPuericultura;
import com.upsaude.entity.estabelecimento.Estabelecimentos;
import com.upsaude.entity.profissional.ProfissionaisSaude;
import com.upsaude.entity.puericultura.Puericultura;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.service.support.profissionaissaude.ProfissionaisSaudeTenantEnforcer;
import com.upsaude.service.support.puericultura.PuericulturaTenantEnforcer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConsultaPuericulturaRelacionamentosHandler {

    private final PuericulturaTenantEnforcer puericulturaTenantEnforcer;
    private final ProfissionaisSaudeTenantEnforcer profissionaisSaudeTenantEnforcer;

    public void resolver(ConsultaPuericultura entity, ConsultaPuericulturaRequest request, UUID tenantId, Tenant tenant) {
        if (request == null) return;

        if (request.getPuericultura() != null) {
            Puericultura puericultura = puericulturaTenantEnforcer.validarAcessoCompleto(request.getPuericultura(), tenantId);
            entity.setPuericultura(puericultura);
        }

        if (request.getProfissional() != null) {
            ProfissionaisSaude profissional = profissionaisSaudeTenantEnforcer.validarAcesso(request.getProfissional(), tenantId);
            entity.setProfissional(profissional);
        }

        entity.setTenant(Objects.requireNonNull(tenant, "tenant é obrigatório"));

        Estabelecimentos estabelecimento = null;
        if (entity.getPuericultura() != null) {
            estabelecimento = entity.getPuericultura().getEstabelecimento();
        }
        if (estabelecimento != null) {
            entity.setEstabelecimento(estabelecimento);
        }
    }
}

