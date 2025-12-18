package com.upsaude.service.support.cirurgia;

import com.upsaude.entity.cirurgia.Cirurgia;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.cirurgia.CirurgiaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CirurgiaTenantEnforcer {

    private final CirurgiaRepository repository;

    public Cirurgia validarAcesso(UUID id, UUID tenantId) {
        log.debug("Validando acesso à cirurgia. ID: {}, tenant: {}", id, tenantId);
        return repository.findByIdAndTenant(id, tenantId)
            .orElseThrow(() -> {
                log.warn("Cirurgia não encontrada. ID: {}, tenant: {}", id, tenantId);
                return new NotFoundException("Cirurgia não encontrada com ID: " + id);
            });
    }

    public Cirurgia validarAcessoCompleto(UUID id, UUID tenantId) {
        return validarAcesso(id, tenantId);
    }
}

