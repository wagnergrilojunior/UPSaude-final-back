package com.upsaude.service.api.support.financeiro.contafinanceira;

import com.upsaude.api.request.financeiro.ContaFinanceiraRequest;
import com.upsaude.entity.financeiro.ContaFinanceira;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContaFinanceiraRelacionamentosHandler {

    public void resolver(ContaFinanceira entity, ContaFinanceiraRequest request, UUID tenantId, Tenant tenant) {
        if (request == null) return;
        entity.setTenant(Objects.requireNonNull(tenant, "tenant é obrigatório"));
    }
}

