package com.upsaude.service.api.support.historicoclinico;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.entity.clinica.prontuario.HistoricoClinico;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.clinica.prontuario.HistoricoClinicoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class HistoricoClinicoTenantEnforcer {

    private final HistoricoClinicoRepository repository;

    public HistoricoClinico validarAcesso(UUID id, UUID tenantId) {
        log.debug("Validando acesso ao histórico clínico. ID: {}, tenant: {}", id, tenantId);
        return repository.findByIdAndTenant(id, tenantId)
            .orElseThrow(() -> {
                log.warn("Histórico clínico não encontrado. ID: {}, tenant: {}", id, tenantId);
                return new NotFoundException("Histórico clínico não encontrado com ID: " + id);
            });
    }

    public HistoricoClinico validarAcessoCompleto(UUID id, UUID tenantId) {
        return validarAcesso(id, tenantId);
    }
}
