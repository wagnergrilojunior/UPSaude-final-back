package com.upsaude.service.api.support.financeiro.plano;

import com.upsaude.entity.financeiro.PlanoContas;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.financeiro.PlanoContasRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlanoContasTenantEnforcer {

    private final PlanoContasRepository repository;

    public PlanoContas validarAcesso(UUID id, UUID tenantId) {
        log.debug("Validando acesso ao plano de contas. ID: {}, tenant: {}", id, tenantId);
        return repository.findByIdAndTenant(id, tenantId)
                .orElseThrow(() -> {
                    log.warn("Plano de contas não encontrado. ID: {}, tenant: {}", id, tenantId);
                    return new NotFoundException("Plano de contas não encontrado com ID: " + id);
                });
    }

    public PlanoContas validarAcessoCompleto(UUID id, UUID tenantId) {
        return validarAcesso(id, tenantId);
    }
}

