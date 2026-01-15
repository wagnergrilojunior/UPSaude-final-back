package com.upsaude.service.api.support.financeiro.competencia;

import com.upsaude.entity.financeiro.CompetenciaFinanceira;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.financeiro.CompetenciaFinanceiraRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompetenciaFinanceiraTenantEnforcer {

    private final CompetenciaFinanceiraRepository repository;

    public CompetenciaFinanceira validarAcesso(UUID id, UUID tenantId) {
        log.debug("Validando acesso à competência financeira. ID: {}, tenant: {}", id, tenantId);
        return repository.findByIdAndTenant(id, tenantId)
                .orElseThrow(() -> {
                    log.warn("Competência financeira não encontrada. ID: {}, tenant: {}", id, tenantId);
                    return new NotFoundException("Competência financeira não encontrada com ID: " + id);
                });
    }

    public CompetenciaFinanceira validarAcessoCompleto(UUID id, UUID tenantId) {
        return validarAcesso(id, tenantId);
    }
}
