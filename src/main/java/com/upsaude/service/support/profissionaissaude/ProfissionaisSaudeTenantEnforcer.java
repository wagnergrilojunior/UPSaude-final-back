package com.upsaude.service.support.profissionaissaude;

import com.upsaude.entity.profissional.ProfissionaisSaude;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.profissional.ProfissionaisSaudeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfissionaisSaudeTenantEnforcer {

    private final ProfissionaisSaudeRepository profissionaisSaudeRepository;

    public ProfissionaisSaude validarAcesso(UUID id, UUID tenantId) {
        log.debug("Validando acesso ao profissional de saúde ID: {} para tenant: {}", id, tenantId);
        
        return profissionaisSaudeRepository.findByIdAndTenant(id, tenantId)
                .orElseThrow(() -> {
                    log.warn("Profissional de saúde não encontrado com ID: {} para tenant: {}", id, tenantId);
                    return new NotFoundException("Profissional de saúde não encontrado com ID: " + id);
                });
    }

    public ProfissionaisSaude validarAcessoCompleto(UUID id, UUID tenantId) {
        log.debug("Validando acesso completo ao profissional de saúde ID: {} para tenant: {}", id, tenantId);
        
        return profissionaisSaudeRepository.findByIdCompletoAndTenant(id, tenantId)
                .orElseThrow(() -> {
                    log.warn("Profissional de saúde não encontrado com ID: {} para tenant: {}", id, tenantId);
                    return new NotFoundException("Profissional de saúde não encontrado com ID: " + id);
                });
    }
}
