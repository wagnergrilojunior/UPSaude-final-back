package com.upsaude.service.api.support.financeiro.contacontabil;

import com.upsaude.entity.financeiro.ContaContabil;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.financeiro.ContaContabilRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContaContabilTenantEnforcer {

    private final ContaContabilRepository repository;

    public ContaContabil validarAcesso(UUID id, UUID tenantId) {
        return repository.findByIdAndTenant(id, tenantId)
                .orElseThrow(() -> new NotFoundException("Conta contábil não encontrada com ID: " + id));
    }

    public ContaContabil validarAcessoCompleto(UUID id, UUID tenantId) {
        return validarAcesso(id, tenantId);
    }
}

