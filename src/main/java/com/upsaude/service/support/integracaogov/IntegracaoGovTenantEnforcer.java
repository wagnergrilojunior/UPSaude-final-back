package com.upsaude.service.support.integracaogov;

import com.upsaude.entity.IntegracaoGov;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.IntegracaoGovRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class IntegracaoGovTenantEnforcer {

    private final IntegracaoGovRepository repository;

    public IntegracaoGov validarAcesso(UUID id, UUID tenantId) {
        log.debug("Validando acesso à integração gov. ID: {}, tenant: {}", id, tenantId);
        return repository.findByIdAndTenant(id, tenantId)
            .orElseThrow(() -> {
                log.warn("Integração gov não encontrada. ID: {}, tenant: {}", id, tenantId);
                return new NotFoundException("Integração gov não encontrada com ID: " + id);
            });
    }

    public IntegracaoGov validarAcessoCompleto(UUID id, UUID tenantId) {
        return validarAcesso(id, tenantId);
    }
}
