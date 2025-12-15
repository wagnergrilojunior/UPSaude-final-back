package com.upsaude.service.support.exames;

import com.upsaude.entity.Exames;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.ExamesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExamesTenantEnforcer {

    private final ExamesRepository repository;

    public Exames validarAcesso(UUID id, UUID tenantId) {
        log.debug("Validando acesso ao exame. ID: {}, tenant: {}", id, tenantId);
        return repository.findByIdAndTenant(id, tenantId)
            .orElseThrow(() -> {
                log.warn("Exame não encontrado. ID: {}, tenant: {}", id, tenantId);
                return new NotFoundException("Exame não encontrado com ID: " + id);
            });
    }

    public Exames validarAcessoCompleto(UUID id, UUID tenantId) {
        return validarAcesso(id, tenantId);
    }
}

