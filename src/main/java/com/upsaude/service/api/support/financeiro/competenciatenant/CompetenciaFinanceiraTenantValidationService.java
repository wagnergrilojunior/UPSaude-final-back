package com.upsaude.service.api.support.financeiro.competenciatenant;

import com.upsaude.api.request.financeiro.CompetenciaFinanceiraTenantRequest;
import com.upsaude.exception.ConflictException;
import com.upsaude.repository.financeiro.CompetenciaFinanceiraTenantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompetenciaFinanceiraTenantValidationService {

    private final CompetenciaFinanceiraTenantRepository repository;

    public void validarUnicidadeParaCriacao(CompetenciaFinanceiraTenantRequest request, UUID tenantId) {
        validarCompetenciaUnica(null, request, tenantId);
    }

    public void validarUnicidadeParaAtualizacao(UUID id, CompetenciaFinanceiraTenantRequest request, UUID tenantId) {
        validarCompetenciaUnica(id, request, tenantId);
    }

    private void validarCompetenciaUnica(UUID id, CompetenciaFinanceiraTenantRequest request, UUID tenantId) {
        if (request == null || request.getCompetencia() == null) {
            return;
        }
        repository.findByTenantAndCompetencia(tenantId, request.getCompetencia())
                .ifPresent(existing -> {
                    if (id == null || !existing.getId().equals(id)) {
                        throw new ConflictException("Já existe registro de competência para este tenant");
                    }
                });
    }
}

