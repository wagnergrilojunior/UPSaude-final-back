package com.upsaude.service.api.support.permissoes;

import com.upsaude.entity.sistema.usuario.Permissoes;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.sistema.usuario.PermissoesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PermissoesTenantEnforcer {

    private final PermissoesRepository repository;

    public Permissoes validarAcesso(UUID id, UUID tenantId) {
        log.debug("Validando acesso à permissão. ID: {}, tenant: {}", id, tenantId);
        return repository.findByIdAndTenant(id, tenantId)
            .orElseThrow(() -> {
                log.warn("Permissão não encontrada. ID: {}, tenant: {}", id, tenantId);
                return new NotFoundException("Permissão não encontrada com ID: " + id);
            });
    }

    public Permissoes validarAcessoCompleto(UUID id, UUID tenantId) {
        return validarAcesso(id, tenantId);
    }
}

