package com.upsaude.service.support.consultaprenatal;


import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.entity.clinica.atendimento.ConsultaPreNatal;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.clinica.atendimento.ConsultaPreNatalRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsultaPreNatalTenantEnforcer {

    private final ConsultaPreNatalRepository repository;

    public ConsultaPreNatal validarAcesso(UUID id, UUID tenantId) {
        log.debug("Validando acesso à consulta pré-natal. ID: {}, tenant: {}", id, tenantId);
        return repository.findByIdAndTenant(id, tenantId)
            .orElseThrow(() -> {
                log.warn("Consulta pré-natal não encontrada. ID: {}, tenant: {}", id, tenantId);
                return new NotFoundException("Consulta pré-natal não encontrada com ID: " + id);
            });
    }

    public ConsultaPreNatal validarAcessoCompleto(UUID id, UUID tenantId) {
        return validarAcesso(id, tenantId);
    }
}

