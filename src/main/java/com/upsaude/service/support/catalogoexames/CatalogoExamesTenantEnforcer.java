package com.upsaude.service.support.catalogoexames;

import com.upsaude.entity.CatalogoExames;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.CatalogoExamesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CatalogoExamesTenantEnforcer {

    private final CatalogoExamesRepository repository;

    public CatalogoExames validarAcesso(UUID id, UUID tenantId) {
        log.debug("Validando acesso ao catálogo de exames. ID: {}, tenant: {}", id, tenantId);
        return repository.findByIdAndTenant(id, tenantId)
                .orElseThrow(() -> {
                    log.warn("Exame não encontrado no catálogo. ID: {}, tenant: {}", id, tenantId);
                    return new NotFoundException("Exame não encontrado no catálogo com ID: " + id);
                });
    }

    public CatalogoExames validarAcessoCompleto(UUID id, UUID tenantId) {
        return validarAcesso(id, tenantId);
    }
}
