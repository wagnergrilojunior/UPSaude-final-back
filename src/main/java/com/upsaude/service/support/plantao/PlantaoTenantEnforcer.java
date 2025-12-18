package com.upsaude.service.support.plantao;

import com.upsaude.entity.equipe.Plantao;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.profissional.equipe.PlantaoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlantaoTenantEnforcer {

    private final PlantaoRepository repository;

    public Plantao validarAcesso(UUID id, UUID tenantId) {
        log.debug("Validando acesso ao plantão. ID: {}, tenant: {}", id, tenantId);
        return repository.findByIdAndTenant(id, tenantId)
            .orElseThrow(() -> {
                log.warn("Plantão não encontrado. ID: {}, tenant: {}", id, tenantId);
                return new NotFoundException("Plantão não encontrado com ID: " + id);
            });
    }

    public Plantao validarAcessoCompleto(UUID id, UUID tenantId) {
        return validarAcesso(id, tenantId);
    }
}

