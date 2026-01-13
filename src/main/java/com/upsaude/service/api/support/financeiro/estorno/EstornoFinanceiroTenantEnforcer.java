package com.upsaude.service.api.support.financeiro.estorno;

import com.upsaude.entity.financeiro.EstornoFinanceiro;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.financeiro.EstornoFinanceiroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EstornoFinanceiroTenantEnforcer {

    private final EstornoFinanceiroRepository repository;

    public EstornoFinanceiro validarAcesso(UUID id, UUID tenantId) {
        return repository.findByIdAndTenant(id, tenantId)
                .orElseThrow(() -> new NotFoundException("Estorno financeiro não encontrado com ID: " + id));
    }

    public EstornoFinanceiro validarAcessoCompleto(UUID id, UUID tenantId) {
        return validarAcesso(id, tenantId);
    }
}

