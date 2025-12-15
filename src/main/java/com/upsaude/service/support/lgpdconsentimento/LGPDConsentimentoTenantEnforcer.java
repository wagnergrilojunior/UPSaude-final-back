package com.upsaude.service.support.lgpdconsentimento;

import com.upsaude.entity.LGPDConsentimento;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.LGPDConsentimentoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class LGPDConsentimentoTenantEnforcer {

    private final LGPDConsentimentoRepository repository;

    public LGPDConsentimento validarAcesso(UUID id, UUID tenantId) {
        log.debug("Validando acesso ao consentimento LGPD. ID: {}, tenant: {}", id, tenantId);
        return repository.findByIdAndTenant(id, tenantId)
            .orElseThrow(() -> {
                log.warn("Consentimento LGPD não encontrado. ID: {}, tenant: {}", id, tenantId);
                return new NotFoundException("Consentimento LGPD não encontrado com ID: " + id);
            });
    }

    public LGPDConsentimento validarAcessoCompleto(UUID id, UUID tenantId) {
        return validarAcesso(id, tenantId);
    }
}
