package com.upsaude.service.api.support.responsavellegal;

import com.upsaude.entity.paciente.ResponsavelLegal;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.paciente.ResponsavelLegalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResponsavelLegalTenantEnforcer {

    private final ResponsavelLegalRepository repository;

    public ResponsavelLegal validarAcesso(UUID id, UUID tenantId) {
        log.debug("Validando acesso ao responsável legal. ID: {}, tenant: {}", id, tenantId);
        return repository.findByIdAndTenant(id, tenantId)
            .orElseThrow(() -> {
                log.warn("Responsável legal não encontrado. ID: {}, tenant: {}", id, tenantId);
                return new NotFoundException("Responsável legal não encontrado com ID: " + id);
            });
    }

    public ResponsavelLegal validarAcessoCompleto(UUID id, UUID tenantId) {
        return validarAcesso(id, tenantId);
    }
}
