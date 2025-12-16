package com.upsaude.service.support.planejamentofamiliar;

import com.upsaude.entity.PlanejamentoFamiliar;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.PlanejamentoFamiliarRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlanejamentoFamiliarTenantEnforcer {

    private final PlanejamentoFamiliarRepository repository;

    public PlanejamentoFamiliar validarAcesso(UUID id, UUID tenantId) {
        log.debug("Validando acesso ao planejamento familiar. ID: {}, tenant: {}", id, tenantId);
        return repository.findByIdAndTenant(id, tenantId)
            .orElseThrow(() -> {
                log.warn("Planejamento familiar não encontrado. ID: {}, tenant: {}", id, tenantId);
                return new NotFoundException("Planejamento familiar não encontrado com ID: " + id);
            });
    }

    public PlanejamentoFamiliar validarAcessoCompleto(UUID id, UUID tenantId) {
        return validarAcesso(id, tenantId);
    }
}

