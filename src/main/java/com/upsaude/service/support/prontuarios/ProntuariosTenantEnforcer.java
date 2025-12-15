package com.upsaude.service.support.prontuarios;

import com.upsaude.entity.Prontuarios;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.ProntuariosRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

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

