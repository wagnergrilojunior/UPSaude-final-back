package com.upsaude.service.support.movimentacoesestoque;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.entity.estabelecimento.estoque.MovimentacoesEstoque;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.estabelecimento.estoque.MovimentacoesEstoqueRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovimentacoesEstoqueTenantEnforcer {

    private final MovimentacoesEstoqueRepository repository;

    public MovimentacoesEstoque validarAcesso(UUID id, UUID tenantId) {
        log.debug("Validando acesso à movimentação de estoque. ID: {}, tenant: {}", id, tenantId);
        return repository.findByIdAndTenant(id, tenantId)
            .orElseThrow(() -> {
                log.warn("Movimentação de estoque não encontrada. ID: {}, tenant: {}", id, tenantId);
                return new NotFoundException("Movimentação de estoque não encontrada com ID: " + id);
            });
    }

    public MovimentacoesEstoque validarAcessoCompleto(UUID id, UUID tenantId) {
        return validarAcesso(id, tenantId);
    }
}
