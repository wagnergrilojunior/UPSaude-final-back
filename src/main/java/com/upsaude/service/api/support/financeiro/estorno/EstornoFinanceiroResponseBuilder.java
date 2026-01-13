package com.upsaude.service.api.support.financeiro.estorno;

import com.upsaude.api.response.financeiro.EstornoFinanceiroResponse;
import com.upsaude.entity.financeiro.EstornoFinanceiro;
import com.upsaude.mapper.financeiro.EstornoFinanceiroMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EstornoFinanceiroResponseBuilder {

    private final EstornoFinanceiroMapper mapper;

    public EstornoFinanceiroResponse build(EstornoFinanceiro entity) {
        return mapper.toResponse(entity);
    }
}

