package com.upsaude.service.support.infraestruturaestabelecimento;

import com.upsaude.api.request.estabelecimento.InfraestruturaEstabelecimentoRequest;
import com.upsaude.entity.estabelecimento.Estabelecimentos;
import com.upsaude.entity.estabelecimento.InfraestruturaEstabelecimento;
import com.upsaude.entity.sistema.Tenant;
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
