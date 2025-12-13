package com.upsaude.service.support.atendimento;

import com.upsaude.entity.Atendimento;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.AtendimentoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

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
