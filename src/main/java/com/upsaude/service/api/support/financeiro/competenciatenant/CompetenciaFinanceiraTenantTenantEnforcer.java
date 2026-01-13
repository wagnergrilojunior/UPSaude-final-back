package com.upsaude.service.api.support.financeiro.competenciatenant;

import com.upsaude.entity.financeiro.CompetenciaFinanceiraTenant;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.financeiro.CompetenciaFinanceiraTenantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompetenciaFinanceiraTenantTenantEnforcer {

    private final CompetenciaFinanceiraTenantRepository repository;

    public CompetenciaFinanceiraTenant validarAcesso(UUID id, UUID tenantId) {
        return repository.findByIdAndTenant(id, tenantId)
                .orElseThrow(() -> new NotFoundException("Competência (tenant) não encontrada com ID: " + id));
    }

    public CompetenciaFinanceiraTenant validarAcessoCompleto(UUID id, UUID tenantId) {
        return validarAcesso(id, tenantId);
    }
}

