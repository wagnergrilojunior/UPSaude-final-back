package com.upsaude.service.api.support.financeiro.lancamento;

import com.upsaude.entity.financeiro.LancamentoFinanceiro;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.financeiro.LancamentoFinanceiroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LancamentoFinanceiroTenantEnforcer {

    private final LancamentoFinanceiroRepository repository;

    public LancamentoFinanceiro validarAcesso(UUID id, UUID tenantId) {
        return repository.findByIdAndTenant(id, tenantId)
                .orElseThrow(() -> new NotFoundException("Lançamento financeiro não encontrado com ID: " + id));
    }

    public LancamentoFinanceiro validarAcessoCompleto(UUID id, UUID tenantId) {
        return validarAcesso(id, tenantId);
    }
}

