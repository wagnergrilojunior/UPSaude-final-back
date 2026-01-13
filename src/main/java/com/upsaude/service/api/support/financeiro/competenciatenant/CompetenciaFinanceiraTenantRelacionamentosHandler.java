package com.upsaude.service.api.support.financeiro.competenciatenant;

import com.upsaude.api.request.financeiro.CompetenciaFinanceiraTenantRequest;
import com.upsaude.entity.financeiro.CompetenciaFinanceira;
import com.upsaude.entity.financeiro.CompetenciaFinanceiraTenant;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.repository.financeiro.CompetenciaFinanceiraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompetenciaFinanceiraTenantRelacionamentosHandler {

    private final CompetenciaFinanceiraRepository competenciaFinanceiraRepository;

    public void resolver(CompetenciaFinanceiraTenant entity, CompetenciaFinanceiraTenantRequest request, UUID tenantId, Tenant tenant) {
        if (request == null) return;

        entity.setTenant(Objects.requireNonNull(tenant, "tenant é obrigatório"));

        CompetenciaFinanceira competencia = competenciaFinanceiraRepository.findById(request.getCompetencia())
                .orElseThrow(() -> new BadRequestException("Competência financeira não encontrada com ID: " + request.getCompetencia()));
        entity.setCompetencia(competencia);
    }
}

