package com.upsaude.service.support.visitasdomiciliares;

import com.upsaude.entity.visita.VisitasDomiciliares;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.saude_publica.visita.VisitasDomiciliaresRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class VisitasDomiciliaresTenantEnforcer {

    private final VisitasDomiciliaresRepository repository;

    public VisitasDomiciliares validarAcesso(UUID id, UUID tenantId) {
        log.debug("Validando acesso à visita domiciliar. ID: {}, tenant: {}", id, tenantId);
        return repository.findByIdAndTenant(id, tenantId)
            .orElseThrow(() -> {
                log.warn("Visita domiciliar não encontrada. ID: {}, tenant: {}", id, tenantId);
                return new NotFoundException("Visita domiciliar não encontrada com ID: " + id);
            });
    }

    public VisitasDomiciliares validarAcessoCompleto(UUID id, UUID tenantId) {
        return validarAcesso(id, tenantId);
    }
}

