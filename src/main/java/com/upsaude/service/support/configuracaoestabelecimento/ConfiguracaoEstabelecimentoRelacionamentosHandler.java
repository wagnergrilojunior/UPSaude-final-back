package com.upsaude.service.support.configuracaoestabelecimento;

import com.upsaude.api.request.ConfiguracaoEstabelecimentoRequest;
import com.upsaude.entity.ConfiguracaoEstabelecimento;
import com.upsaude.entity.Estabelecimentos;
import com.upsaude.entity.Tenant;
import com.upsaude.service.support.estabelecimentos.EstabelecimentosTenantEnforcer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConfiguracaoEstabelecimentoRelacionamentosHandler {

    private final EstabelecimentosTenantEnforcer estabelecimentosTenantEnforcer;

    public void resolver(ConfiguracaoEstabelecimento entity, ConfiguracaoEstabelecimentoRequest request, UUID tenantId, Tenant tenant) {
        if (request == null) return;
        entity.setTenant(Objects.requireNonNull(tenant, "tenant é obrigatório"));

        Estabelecimentos estabelecimento = estabelecimentosTenantEnforcer.validarAcesso(request.getEstabelecimento(), tenantId);
        entity.setEstabelecimento(estabelecimento);
    }
}

