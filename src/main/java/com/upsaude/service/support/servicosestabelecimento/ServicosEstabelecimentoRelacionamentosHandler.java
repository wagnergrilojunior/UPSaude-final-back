package com.upsaude.service.support.servicosestabelecimento;

import com.upsaude.api.request.estabelecimento.ServicosEstabelecimentoRequest;
import com.upsaude.entity.estabelecimento.Estabelecimentos;
import com.upsaude.entity.estabelecimento.ServicosEstabelecimento;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.service.support.estabelecimentos.EstabelecimentosTenantEnforcer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ServicosEstabelecimentoRelacionamentosHandler {

    private final EstabelecimentosTenantEnforcer estabelecimentosTenantEnforcer;

    public void resolver(ServicosEstabelecimento entity, ServicosEstabelecimentoRequest request, UUID tenantId, Tenant tenant) {
        if (request == null) return;
        entity.setTenant(Objects.requireNonNull(tenant, "tenant é obrigatório"));

        Estabelecimentos estabelecimento = estabelecimentosTenantEnforcer.validarAcesso(request.getEstabelecimento(), tenantId);
        entity.setEstabelecimento(estabelecimento);
    }
}

