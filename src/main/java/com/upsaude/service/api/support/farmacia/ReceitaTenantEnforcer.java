package com.upsaude.service.api.support.farmacia;

import com.upsaude.entity.farmacia.Receita;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.farmacia.ReceitaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReceitaTenantEnforcer {

    private final ReceitaRepository receitaRepository;

    public Receita validarAcesso(UUID id, UUID tenantId) {
        log.debug("Validando acesso à receita ID: {} para tenant: {}", id, tenantId);

        return receitaRepository.findByIdAndTenant(id, tenantId)
                .orElseThrow(() -> {
                    log.warn("Receita não encontrada com ID: {} para tenant: {}", id, tenantId);
                    return new NotFoundException("Receita não encontrada com ID: " + id);
                });
    }
}
