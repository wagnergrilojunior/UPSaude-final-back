package com.upsaude.service.support.historicoclinico;

import com.upsaude.entity.HistoricoClinico;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.HistoricoClinicoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class HistoricoClinicoTenantEnforcer {

    private final HistoricoClinicoRepository repository;

    public HistoricoClinico validarAcesso(UUID id, UUID tenantId) {
        log.debug("Validando acesso ao histórico clínico. ID: {}, tenant: {}", id, tenantId);
        return repository.findByIdAndTenant(id, tenantId)
            .orElseThrow(() -> {
                log.warn("Registro não encontrado. ID: {}, tenant: {}", id, tenantId);
                return new NotFoundException("Registro do histórico clínico não encontrado com ID: " + id);
            });
    }

    public HistoricoClinico validarAcessoCompleto(UUID id, UUID tenantId) {
        return validarAcesso(id, tenantId);
    }
}
