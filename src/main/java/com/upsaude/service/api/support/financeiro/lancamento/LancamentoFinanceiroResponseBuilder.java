package com.upsaude.service.api.support.financeiro.lancamento;

import com.upsaude.api.response.financeiro.LancamentoFinanceiroResponse;
import com.upsaude.entity.financeiro.LancamentoFinanceiro;
import com.upsaude.mapper.financeiro.LancamentoFinanceiroMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LancamentoFinanceiroResponseBuilder {

    private final LancamentoFinanceiroMapper mapper;

    public LancamentoFinanceiroResponse build(LancamentoFinanceiro entity) {
        return mapper.toResponse(entity);
    }
}

