package com.upsaude.service.api.support.financeiro.plano;

import com.upsaude.api.response.financeiro.PlanoContasResponse;
import com.upsaude.entity.financeiro.PlanoContas;
import com.upsaude.mapper.financeiro.PlanoContasMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlanoContasResponseBuilder {

    private final PlanoContasMapper mapper;

    public PlanoContasResponse build(PlanoContas entity) {
        return mapper.toResponse(entity);
    }
}

