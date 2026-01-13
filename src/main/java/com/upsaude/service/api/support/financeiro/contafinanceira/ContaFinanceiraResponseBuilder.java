package com.upsaude.service.api.support.financeiro.contafinanceira;

import com.upsaude.api.response.financeiro.ContaFinanceiraResponse;
import com.upsaude.entity.financeiro.ContaFinanceira;
import com.upsaude.mapper.financeiro.ContaFinanceiraMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContaFinanceiraResponseBuilder {

    private final ContaFinanceiraMapper mapper;

    public ContaFinanceiraResponse build(ContaFinanceira entity) {
        return mapper.toResponse(entity);
    }
}

