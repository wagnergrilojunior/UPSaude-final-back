package com.upsaude.service.support.historicohabilitacaoprofissional;

import com.upsaude.api.request.profissional.HistoricoHabilitacaoProfissionalRequest;
import com.upsaude.entity.profissional.HistoricoHabilitacaoProfissional;
import com.upsaude.entity.profissional.ProfissionaisSaude;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.service.support.profissionaissaude.ProfissionaisSaudeTenantEnforcer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HistoricoHabilitacaoProfissionalRelacionamentosHandler {

    private final ProfissionaisSaudeTenantEnforcer profissionaisSaudeTenantEnforcer;

    public void resolver(HistoricoHabilitacaoProfissional entity, HistoricoHabilitacaoProfissionalRequest request, UUID tenantId, Tenant tenant) {
        if (request == null) return;

        entity.setTenant(Objects.requireNonNull(tenant, "tenant é obrigatório"));

        ProfissionaisSaude profissional = profissionaisSaudeTenantEnforcer.validarAcesso(Objects.requireNonNull(request.getProfissional(), "profissional"), tenantId);
        entity.setProfissional(profissional);

        if (profissional.getEstabelecimento() != null) {
            entity.setEstabelecimento(profissional.getEstabelecimento());
        } else {
            entity.setEstabelecimento(null);
        }
    }
}
