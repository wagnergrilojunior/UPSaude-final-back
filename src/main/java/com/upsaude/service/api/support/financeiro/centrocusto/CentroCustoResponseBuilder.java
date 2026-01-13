package com.upsaude.service.api.support.financeiro.centrocusto;

import com.upsaude.api.response.financeiro.CentroCustoResponse;
import com.upsaude.entity.financeiro.CentroCusto;
import com.upsaude.mapper.financeiro.CentroCustoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CentroCustoResponseBuilder {

    private final CentroCustoMapper mapper;

    public CentroCustoResponse build(CentroCusto entity) {
        return mapper.toResponse(entity);
    }
}

