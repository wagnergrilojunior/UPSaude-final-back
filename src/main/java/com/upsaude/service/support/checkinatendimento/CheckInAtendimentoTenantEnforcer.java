package com.upsaude.service.support.checkinatendimento;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.entity.clinica.atendimento.CheckInAtendimento;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.clinica.atendimento.CheckInAtendimentoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CheckInAtendimentoTenantEnforcer {

    private final CheckInAtendimentoRepository repository;

    public CheckInAtendimento validarAcesso(UUID id, UUID tenantId) {
        log.debug("Validando acesso ao check-in. ID: {}, tenant: {}", id, tenantId);
        return repository.findByIdAndTenant(id, tenantId)
            .orElseThrow(() -> {
                log.warn("Check-in não encontrado. ID: {}, tenant: {}", id, tenantId);
                return new NotFoundException("Check-in não encontrado com ID: " + id);
            });
    }

    public CheckInAtendimento validarAcessoCompleto(UUID id, UUID tenantId) {
        return validarAcesso(id, tenantId);
    }
}
