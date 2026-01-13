package com.upsaude.service.api.support.financeiro.plano;

import com.upsaude.api.request.financeiro.PlanoContasRequest;
import com.upsaude.entity.financeiro.PlanoContas;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PlanoContasRelacionamentosHandler {

    public void resolver(PlanoContas entity, PlanoContasRequest request, UUID tenantId, Tenant tenant) {
        if (request == null) return;
        entity.setTenant(Objects.requireNonNull(tenant, "tenant é obrigatório"));
    }
}

