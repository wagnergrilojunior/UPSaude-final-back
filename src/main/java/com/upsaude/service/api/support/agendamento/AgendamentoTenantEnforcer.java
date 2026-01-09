package com.upsaude.service.api.support.agendamento;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.entity.agendamento.Agendamento;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.agendamento.AgendamentoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AgendamentoTenantEnforcer {

    private final AgendamentoRepository repository;

    public Agendamento validarAcesso(UUID id, UUID tenantId) {
        log.debug("Validando acesso ao agendamento. ID: {}, tenant: {}", id, tenantId);
        return repository.findByIdAndTenant(id, tenantId)
            .orElseThrow(() -> {
                log.warn("Agendamento não encontrado. ID: {}, tenant: {}", id, tenantId);
                return new NotFoundException("Agendamento não encontrado com ID: " + id);
            });
    }

    public Agendamento validarAcessoCompleto(UUID id, UUID tenantId) {
        return repository.findByIdCompletoAndTenant(id, tenantId)
            .orElseThrow(() -> {
                log.warn("Agendamento não encontrado. ID: {}, tenant: {}", id, tenantId);
                return new NotFoundException("Agendamento não encontrado com ID: " + id);
            });
    }
}

