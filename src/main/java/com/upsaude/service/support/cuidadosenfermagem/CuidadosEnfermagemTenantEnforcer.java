package com.upsaude.service.support.cuidadosenfermagem;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.entity.enfermagem.CuidadosEnfermagem;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.enfermagem.CuidadosEnfermagemRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CuidadosEnfermagemTenantEnforcer {

    private final CuidadosEnfermagemRepository repository;

    public CuidadosEnfermagem validarAcesso(UUID id, UUID tenantId) {
        log.debug("Validando acesso aos cuidados de enfermagem. ID: {}, tenant: {}", id, tenantId);
        return repository.findByIdAndTenant(id, tenantId)
            .orElseThrow(() -> {
                log.warn("Cuidados de enfermagem não encontrados. ID: {}, tenant: {}", id, tenantId);
                return new NotFoundException("Cuidados de enfermagem não encontrados com ID: " + id);
            });
    }

    public CuidadosEnfermagem validarAcessoCompleto(UUID id, UUID tenantId) {
        return validarAcesso(id, tenantId);
    }
}
