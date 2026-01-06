package com.upsaude.service.api.support.prontuarios;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.entity.clinica.prontuario.Prontuario;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.clinica.prontuario.ProntuarioRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProntuarioTenantEnforcer {

    private final ProntuarioRepository repository;

    public Prontuario validarAcesso(UUID id, UUID tenantId) {
        log.debug("Validando acesso ao prontuário. ID: {}, tenant: {}", id, tenantId);
        return repository.findByIdAndTenant(id, tenantId)
            .orElseThrow(() -> {
                log.warn("Prontuário não encontrado. ID: {}, tenant: {}", id, tenantId);
                return new NotFoundException("Prontuário não encontrado com ID: " + id);
            });
    }

    public Prontuario validarAcessoCompleto(UUID id, UUID tenantId) {
        return validarAcesso(id, tenantId);
    }
}

