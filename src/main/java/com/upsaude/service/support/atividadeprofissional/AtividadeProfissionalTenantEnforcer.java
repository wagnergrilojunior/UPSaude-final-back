package com.upsaude.service.support.atividadeprofissional;

import com.upsaude.entity.profissional.AtividadeProfissional;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.profissional.AtividadeProfissionalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AtividadeProfissionalTenantEnforcer {

    private final AtividadeProfissionalRepository repository;

    public AtividadeProfissional validarAcesso(UUID id, UUID tenantId) {
        log.debug("Validando acesso à atividade profissional. ID: {}, tenant: {}", id, tenantId);
        return repository.findByIdAndTenant(id, tenantId)
            .orElseThrow(() -> {
                log.warn("Atividade profissional não encontrada. ID: {}, tenant: {}", id, tenantId);
                return new NotFoundException("Atividade profissional não encontrada com ID: " + id);
            });
    }

    public AtividadeProfissional validarAcessoCompleto(UUID id, UUID tenantId) {
        return validarAcesso(id, tenantId);
    }
}

