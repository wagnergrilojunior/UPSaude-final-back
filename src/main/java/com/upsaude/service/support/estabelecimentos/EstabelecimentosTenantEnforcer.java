package com.upsaude.service.support.estabelecimentos;

import com.upsaude.entity.estabelecimento.Estabelecimentos;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.estabelecimento.EstabelecimentosRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class EstabelecimentosTenantEnforcer {

    private final EstabelecimentosRepository repository;

    public Estabelecimentos validarAcesso(UUID id, UUID tenantId) {
        log.debug("Validando acesso ao estabelecimento. ID: {}, tenant: {}", id, tenantId);
        return repository.findByIdAndTenant(id, tenantId)
            .orElseThrow(() -> {
                log.warn("Estabelecimento não encontrado. ID: {}, tenant: {}", id, tenantId);
                return new NotFoundException("Estabelecimento não encontrado com ID: " + id);
            });
    }

    public Estabelecimentos validarAcessoCompleto(UUID id, UUID tenantId) {
        return validarAcesso(id, tenantId);
    }
}

