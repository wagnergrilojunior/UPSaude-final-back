package com.upsaude.service.support.cuidadosenfermagem;

import com.upsaude.entity.CuidadosEnfermagem;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.CuidadosEnfermagemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CuidadosEnfermagemTenantEnforcer {

    private final CuidadosEnfermagemRepository repository;

    public CuidadosEnfermagem validarAcesso(UUID id, UUID tenantId) {
        log.debug("Validando acesso ao cuidado de enfermagem. ID: {}, tenant: {}", id, tenantId);
        return repository.findByIdAndTenant(id, tenantId)
            .orElseThrow(() -> {
                log.warn("Cuidado de enfermagem não encontrado. ID: {}, tenant: {}", id, tenantId);
                return new NotFoundException("Cuidado de enfermagem não encontrado com ID: " + id);
            });
    }

    public CuidadosEnfermagem validarAcessoCompleto(UUID id, UUID tenantId) {
        return validarAcesso(id, tenantId);
    }
}
