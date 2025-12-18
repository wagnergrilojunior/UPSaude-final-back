package com.upsaude.service.support.puericultura;

import com.upsaude.entity.saude_publica.puericultura.Puericultura;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.saude_publica.puericultura.PuericulturaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PuericulturaTenantEnforcer {

    private final PuericulturaRepository puericulturaRepository;

    public Puericultura validarAcesso(UUID id, UUID tenantId) {
        log.debug("Validando acesso à puericultura ID: {} para tenant: {}", id, tenantId);

        return puericulturaRepository.findByIdAndTenant(id, tenantId)
                .orElseThrow(() -> {
                    log.warn("Puericultura não encontrada com ID: {} para tenant: {}", id, tenantId);
                    return new NotFoundException("Puericultura não encontrada com ID: " + id);
                });
    }

    public Puericultura validarAcessoCompleto(UUID id, UUID tenantId) {
        log.debug("Validando acesso completo à puericultura ID: {} para tenant: {}", id, tenantId);

        return puericulturaRepository.findByIdAndTenant(id, tenantId)
                .orElseThrow(() -> {
                    log.warn("Puericultura não encontrada com ID: {} para tenant: {}", id, tenantId);
                    return new NotFoundException("Puericultura não encontrada com ID: " + id);
                });
    }
}
