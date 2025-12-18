package com.upsaude.service.support.controleponto;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.entity.profissional.equipe.ControlePonto;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.profissional.equipe.ControlePontoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ControlePontoTenantEnforcer {

    private final ControlePontoRepository repository;

    public ControlePonto validarAcesso(UUID id, UUID tenantId) {
        log.debug("Validando acesso ao controle de ponto. ID: {}, tenant: {}", id, tenantId);
        return repository.findByIdAndTenant(id, tenantId)
            .orElseThrow(() -> {
                log.warn("Controle de ponto não encontrado. ID: {}, tenant: {}", id, tenantId);
                return new NotFoundException("Controle de ponto não encontrado com ID: " + id);
            });
    }

    public ControlePonto validarAcessoCompleto(UUID id, UUID tenantId) {
        return validarAcesso(id, tenantId);
    }
}
