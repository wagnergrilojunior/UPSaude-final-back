package com.upsaude.service.support.equipesaude;

import com.upsaude.entity.equipe.EquipeSaude;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.profissional.equipe.EquipeSaudeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class EquipeSaudeTenantEnforcer {

    private final EquipeSaudeRepository repository;

    public EquipeSaude validarAcesso(UUID id, UUID tenantId) {
        log.debug("Validando acesso à equipe de saúde. ID: {}, tenant: {}", id, tenantId);
        return repository.findByIdAndTenant(id, tenantId)
                .orElseThrow(() -> {
                    log.warn("Equipe de saúde não encontrada. ID: {}, tenant: {}", id, tenantId);
                    return new NotFoundException("Equipe de saúde não encontrada com ID: " + id);
                });
    }

    public EquipeSaude validarAcessoCompleto(UUID id, UUID tenantId) {
        log.debug("Validando acesso completo à equipe de saúde. ID: {}, tenant: {}", id, tenantId);
        return repository.findByIdCompletoAndTenant(id, tenantId)
                .orElseThrow(() -> {
                    log.warn("Equipe de saúde não encontrada (completo). ID: {}, tenant: {}", id, tenantId);
                    return new NotFoundException("Equipe de saúde não encontrada com ID: " + id);
                });
    }
}
