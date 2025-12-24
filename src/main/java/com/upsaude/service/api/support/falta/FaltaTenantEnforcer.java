package com.upsaude.service.api.support.falta;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.entity.profissional.equipe.Falta;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.profissional.equipe.FaltaRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class FaltaTenantEnforcer {

    private final FaltaRepository repository;

    public Falta validarAcesso(UUID id, UUID tenantId) {
        log.debug("Validando acesso à falta. ID: {}, tenant: {}", id, tenantId);
        return repository.findByIdAndTenant(id, tenantId)
            .orElseThrow(() -> {
                log.warn("Falta não encontrada. ID: {}, tenant: {}", id, tenantId);
                return new NotFoundException("Falta não encontrada com ID: " + id);
            });
    }

    public Falta validarAcessoCompleto(UUID id, UUID tenantId) {
        return validarAcesso(id, tenantId);
    }
}
