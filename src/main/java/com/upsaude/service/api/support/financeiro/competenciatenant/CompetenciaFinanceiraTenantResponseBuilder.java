package com.upsaude.service.api.support.financeiro.competenciatenant;

import com.upsaude.api.response.financeiro.CompetenciaFinanceiraTenantResponse;
import com.upsaude.entity.financeiro.CompetenciaFinanceiraTenant;
import com.upsaude.mapper.financeiro.CompetenciaFinanceiraTenantMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompetenciaFinanceiraTenantResponseBuilder {

    private final CompetenciaFinanceiraTenantMapper mapper;

    public CompetenciaFinanceiraTenantResponse build(CompetenciaFinanceiraTenant entity) {
        return mapper.toResponse(entity);
    }
}

