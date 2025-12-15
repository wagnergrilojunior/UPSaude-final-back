package com.upsaude.service.support.puericultura;

import com.upsaude.entity.Puericultura;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.PuericulturaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PuericulturaTenantEnforcer {

    private final PuericulturaRepository repository;

    public Puericultura validarAcesso(UUID id, UUID tenantId) {
        log.debug("Validando acesso ao acompanhamento de puericultura. ID: {}, tenant: {}", id, tenantId);
        return repository.findByIdAndTenant(id, tenantId)
            .orElseThrow(() -> {
                log.warn("Puericultura não encontrada. ID: {}, tenant: {}", id, tenantId);
                return new NotFoundException("Puericultura não encontrada com ID: " + id);
            });
    }

    public Puericultura validarAcessoCompleto(UUID id, UUID tenantId) {
        return validarAcesso(id, tenantId);
    }
}

