package com.upsaude.service.support.vacinacoes;

import com.upsaude.entity.vacina.Vacinacoes;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.saude_publica.vacina.VacinacoesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class VacinacoesTenantEnforcer {

    private final VacinacoesRepository repository;

    public Vacinacoes validarAcesso(UUID id, UUID tenantId) {
        log.debug("Validando acesso à vacinação. ID: {}, tenant: {}", id, tenantId);
        return repository.findByIdAndTenant(id, tenantId)
            .orElseThrow(() -> {
                log.warn("Vacinação não encontrada. ID: {}, tenant: {}", id, tenantId);
                return new NotFoundException("Vacinação não encontrada com ID: " + id);
            });
    }

    public Vacinacoes validarAcessoCompleto(UUID id, UUID tenantId) {
        return validarAcesso(id, tenantId);
    }
}

