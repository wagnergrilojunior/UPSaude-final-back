package com.upsaude.service.api.support.financeiro.competencia;

import com.upsaude.api.response.financeiro.CompetenciaFinanceiraResponse;
import com.upsaude.entity.financeiro.CompetenciaFinanceira;
import com.upsaude.mapper.financeiro.CompetenciaFinanceiraMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompetenciaFinanceiraResponseBuilder {

    private final CompetenciaFinanceiraMapper mapper;

    public CompetenciaFinanceiraResponse build(CompetenciaFinanceira entity) {
        return mapper.toResponse(entity);
    }
}

