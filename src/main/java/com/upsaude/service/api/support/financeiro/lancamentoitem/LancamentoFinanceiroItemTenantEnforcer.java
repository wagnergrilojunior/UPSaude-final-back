package com.upsaude.service.api.support.financeiro.lancamentoitem;

import com.upsaude.entity.financeiro.LancamentoFinanceiroItem;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.financeiro.LancamentoFinanceiroItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LancamentoFinanceiroItemTenantEnforcer {

    private final LancamentoFinanceiroItemRepository repository;

    public LancamentoFinanceiroItem validarAcesso(UUID id, UUID tenantId) {
        return repository.findByIdAndTenant(id, tenantId)
                .orElseThrow(() -> new NotFoundException("Item de lançamento não encontrado com ID: " + id));
    }

    public LancamentoFinanceiroItem validarAcessoCompleto(UUID id, UUID tenantId) {
        return validarAcesso(id, tenantId);
    }
}

