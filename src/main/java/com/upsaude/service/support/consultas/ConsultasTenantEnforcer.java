package com.upsaude.service.support.consultas;

import com.upsaude.entity.Consultas;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.ConsultasRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsultasTenantEnforcer {

    private final ConsultasRepository repository;

    public Consultas validarAcesso(UUID id, UUID tenantId) {
        log.debug("Validando acesso à consulta. ID: {}, tenant: {}", id, tenantId);
        return repository.findByIdAndTenant(id, tenantId)
            .orElseThrow(() -> {
                log.warn("Consulta não encontrada. ID: {}, tenant: {}", id, tenantId);
                return new NotFoundException("Consulta não encontrada com ID: " + id);
            });
    }

    public Consultas validarAcessoCompleto(UUID id, UUID tenantId) {
        return validarAcesso(id, tenantId);
    }
}
