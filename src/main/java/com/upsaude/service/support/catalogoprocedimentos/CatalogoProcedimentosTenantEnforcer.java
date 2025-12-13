package com.upsaude.service.support.catalogoprocedimentos;

import com.upsaude.entity.CatalogoProcedimentos;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.CatalogoProcedimentosRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CatalogoProcedimentosTenantEnforcer {

    private final CatalogoProcedimentosRepository repository;

    public CatalogoProcedimentos validarAcesso(UUID id, UUID tenantId) {
        log.debug("Validando acesso ao catálogo de procedimentos. ID: {}, tenant: {}", id, tenantId);
        return repository.findByIdAndTenant(id, tenantId)
            .orElseThrow(() -> {
                log.warn("Procedimento não encontrado no catálogo. ID: {}, tenant: {}", id, tenantId);
                return new NotFoundException("Procedimento não encontrado no catálogo com ID: " + id);
            });
    }

    public CatalogoProcedimentos validarAcessoCompleto(UUID id, UUID tenantId) {
        return validarAcesso(id, tenantId);
    }
}

