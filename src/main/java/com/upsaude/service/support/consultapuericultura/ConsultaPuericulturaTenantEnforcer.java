package com.upsaude.service.support.consultapuericultura;

import com.upsaude.entity.atendimento.ConsultaPuericultura;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.atendimento.ConsultaPuericulturaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsultaPuericulturaTenantEnforcer {

    private final ConsultaPuericulturaRepository repository;

    public ConsultaPuericultura validarAcesso(UUID id, UUID tenantId) {
        log.debug("Validando acesso à consulta de puericultura. ID: {}, tenant: {}", id, tenantId);
        return repository.findByIdAndTenant(id, tenantId)
            .orElseThrow(() -> {
                log.warn("Consulta de puericultura não encontrada. ID: {}, tenant: {}", id, tenantId);
                return new NotFoundException("Consulta de puericultura não encontrada com ID: " + id);
            });
    }

    public ConsultaPuericultura validarAcessoCompleto(UUID id, UUID tenantId) {
        return validarAcesso(id, tenantId);
    }
}

