package com.upsaude.service.api.support.configuracaoestabelecimento;

import com.upsaude.entity.estabelecimento.ConfiguracaoEstabelecimento;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.estabelecimento.ConfiguracaoEstabelecimentoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConfiguracaoEstabelecimentoTenantEnforcer {

    private final ConfiguracaoEstabelecimentoRepository repository;

    public ConfiguracaoEstabelecimento validarAcesso(UUID id, UUID tenantId) {
        log.debug("Validando acesso à configuração do estabelecimento. ID: {}, tenant: {}", id, tenantId);
        return repository.findByIdAndTenant(id, tenantId)
            .orElseThrow(() -> {
                log.warn("Configuração do estabelecimento não encontrada. ID: {}, tenant: {}", id, tenantId);
                return new NotFoundException("Configuração do estabelecimento não encontrada com ID: " + id);
            });
    }

    public ConfiguracaoEstabelecimento validarAcessoCompleto(UUID id, UUID tenantId) {
        return validarAcesso(id, tenantId);
    }

    public ConfiguracaoEstabelecimento obterPorEstabelecimentoOrThrow(UUID estabelecimentoId, UUID tenantId) {
        log.debug("Buscando configuração por estabelecimento. estabelecimentoId: {}, tenant: {}", estabelecimentoId, tenantId);
        return repository.findByEstabelecimentoIdAndTenantId(estabelecimentoId, tenantId)
            .orElseThrow(() -> new NotFoundException("Configuração do estabelecimento não encontrada para estabelecimento: " + estabelecimentoId));
    }
}
