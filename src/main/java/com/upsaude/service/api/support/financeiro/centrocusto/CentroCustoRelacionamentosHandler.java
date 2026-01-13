package com.upsaude.service.api.support.financeiro.centrocusto;

import com.upsaude.api.request.financeiro.CentroCustoRequest;
import com.upsaude.entity.financeiro.CentroCusto;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.repository.financeiro.CentroCustoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CentroCustoRelacionamentosHandler {

    private final CentroCustoRepository centroCustoRepository;

    public void resolver(CentroCusto entity, CentroCustoRequest request, UUID tenantId, Tenant tenant) {
        if (request == null) return;

        entity.setTenant(Objects.requireNonNull(tenant, "tenant é obrigatório"));

        if (request.getPai() != null) {
            CentroCusto pai = centroCustoRepository.findByIdAndTenant(request.getPai(), tenantId)
                    .orElseThrow(() -> new BadRequestException("Centro de custo pai não encontrado com ID: " + request.getPai()));
            if (pai.getId().equals(entity.getId())) {
                throw new BadRequestException("Centro de custo pai não pode ser o próprio registro");
            }
            entity.setPai(pai);
        } else {
            entity.setPai(null);
        }
    }
}

