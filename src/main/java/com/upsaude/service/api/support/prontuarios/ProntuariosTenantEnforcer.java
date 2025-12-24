package com.upsaude.service.api.support.prontuarios;


import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.entity.clinica.prontuario.Prontuarios;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.clinica.prontuario.ProntuariosRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProntuariosTenantEnforcer {

    private final ProntuariosRepository repository;

    public Prontuarios validarAcesso(UUID id, UUID tenantId) {
        log.debug("Validando acesso ao prontuário. ID: {}, tenant: {}", id, tenantId);
        return repository.findByIdAndTenant(id, tenantId)
            .orElseThrow(() -> {
                log.warn("Prontuário não encontrado. ID: {}, tenant: {}", id, tenantId);
                return new NotFoundException("Prontuário não encontrado com ID: " + id);
            });
    }

    public Prontuarios validarAcessoCompleto(UUID id, UUID tenantId) {
        return validarAcesso(id, tenantId);
    }
}

