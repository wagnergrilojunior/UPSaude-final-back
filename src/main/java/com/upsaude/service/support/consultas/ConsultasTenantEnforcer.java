package com.upsaude.service.support.consultas;


import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.entity.clinica.atendimento.Consultas;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.clinica.atendimento.ConsultasRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
