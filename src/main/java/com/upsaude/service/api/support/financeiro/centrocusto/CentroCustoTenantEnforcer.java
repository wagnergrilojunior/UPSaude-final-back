package com.upsaude.service.api.support.financeiro.centrocusto;

import com.upsaude.entity.financeiro.CentroCusto;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.financeiro.CentroCustoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CentroCustoTenantEnforcer {

    private final CentroCustoRepository repository;

    public CentroCusto validarAcesso(UUID id, UUID tenantId) {
        return repository.findByIdAndTenant(id, tenantId)
                .orElseThrow(() -> new NotFoundException("Centro de custo não encontrado com ID: " + id));
    }

    public CentroCusto validarAcessoCompleto(UUID id, UUID tenantId) {
        return validarAcesso(id, tenantId);
    }
}

