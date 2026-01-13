package com.upsaude.service.api.support.financeiro.contafinanceira;

import com.upsaude.entity.financeiro.ContaFinanceira;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.financeiro.ContaFinanceiraRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContaFinanceiraTenantEnforcer {

    private final ContaFinanceiraRepository repository;

    public ContaFinanceira validarAcesso(UUID id, UUID tenantId) {
        return repository.findByIdAndTenant(id, tenantId)
                .orElseThrow(() -> new NotFoundException("Conta financeira não encontrada com ID: " + id));
    }

    public ContaFinanceira validarAcessoCompleto(UUID id, UUID tenantId) {
        return validarAcesso(id, tenantId);
    }
}

