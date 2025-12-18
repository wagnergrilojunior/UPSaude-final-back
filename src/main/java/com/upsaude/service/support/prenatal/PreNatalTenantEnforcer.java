package com.upsaude.service.support.prenatal;

import com.upsaude.entity.planejamento.PreNatal;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.saude_publica.planejamento.PreNatalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PreNatalTenantEnforcer {

    private final PreNatalRepository repository;

    public PreNatal validarAcesso(UUID id, UUID tenantId) {
        log.debug("Validando acesso ao pré-natal. ID: {}, tenant: {}", id, tenantId);
        return repository.findByIdAndTenant(id, tenantId)
            .orElseThrow(() -> {
                log.warn("Pré-natal não encontrado. ID: {}, tenant: {}", id, tenantId);
                return new NotFoundException("Pré-natal não encontrado com ID: " + id);
            });
    }

    public PreNatal validarAcessoCompleto(UUID id, UUID tenantId) {
        return validarAcesso(id, tenantId);
    }
}

