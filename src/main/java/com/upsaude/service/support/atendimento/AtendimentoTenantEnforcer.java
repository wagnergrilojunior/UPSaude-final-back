package com.upsaude.service.support.atendimento;


import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.entity.clinica.atendimento.Atendimento;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.clinica.atendimento.AtendimentoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AtendimentoTenantEnforcer {

    private final AtendimentoRepository repository;

    public Atendimento validarAcesso(UUID id, UUID tenantId) {
        log.debug("Validando acesso ao atendimento. ID: {}, tenant: {}", id, tenantId);
        return repository.findByIdAndTenant(id, tenantId)
            .orElseThrow(() -> {
                log.warn("Atendimento não encontrado. ID: {}, tenant: {}", id, tenantId);
                return new NotFoundException("Atendimento não encontrado com ID: " + id);
            });
    }

    public Atendimento validarAcessoCompleto(UUID id, UUID tenantId) {
        return validarAcesso(id, tenantId);
    }
}
