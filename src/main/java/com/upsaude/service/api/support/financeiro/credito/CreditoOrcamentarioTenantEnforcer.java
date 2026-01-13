package com.upsaude.service.api.support.financeiro.credito;

import com.upsaude.entity.financeiro.CreditoOrcamentario;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.financeiro.CreditoOrcamentarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreditoOrcamentarioTenantEnforcer {

    private final CreditoOrcamentarioRepository repository;

    public CreditoOrcamentario validarAcesso(UUID id, UUID tenantId) {
        return repository.findByIdAndTenant(id, tenantId)
                .orElseThrow(() -> new NotFoundException("Crédito orçamentário não encontrado com ID: " + id));
    }

    public CreditoOrcamentario validarAcessoCompleto(UUID id, UUID tenantId) {
        return validarAcesso(id, tenantId);
    }
}

