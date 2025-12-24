package com.upsaude.service.api.support.convenio;

import com.upsaude.entity.convenio.Convenio;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.convenio.ConvenioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConvenioTenantEnforcer {

    private final ConvenioRepository repository;

    public Convenio validarAcesso(UUID id, UUID tenantId) {
        log.debug("Validando acesso ao convênio. ID: {}, tenant: {}", id, tenantId);
        return repository.findByIdAndTenant(id, tenantId)
            .orElseThrow(() -> {
                log.warn("Convênio não encontrado. ID: {}, tenant: {}", id, tenantId);
                return new NotFoundException("Convênio não encontrado com ID: " + id);
            });
    }

    public Convenio validarAcessoCompleto(UUID id, UUID tenantId) {
        return validarAcesso(id, tenantId);
    }
}
