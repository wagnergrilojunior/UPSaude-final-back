package com.upsaude.service.support.infraestruturaestabelecimento;

import com.upsaude.api.request.InfraestruturaEstabelecimentoRequest;
import com.upsaude.entity.Estabelecimentos;
import com.upsaude.entity.InfraestruturaEstabelecimento;
import com.upsaude.entity.Tenant;
import com.upsaude.service.support.estabelecimentos.EstabelecimentosTenantEnforcer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InfraestruturaEstabelecimentoRelacionamentosHandler {

    private final EstabelecimentosTenantEnforcer estabelecimentosTenantEnforcer;

    public void resolver(InfraestruturaEstabelecimento entity, InfraestruturaEstabelecimentoRequest request, UUID tenantId, Tenant tenant) {
        if (request == null) return;

        entity.setTenant(Objects.requireNonNull(tenant, "tenant é obrigatório"));

        Estabelecimentos estabelecimento = estabelecimentosTenantEnforcer.validarAcesso(Objects.requireNonNull(request.getEstabelecimento(), "estabelecimento"), tenantId);
        entity.setEstabelecimento(estabelecimento);
    }
}
