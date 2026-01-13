package com.upsaude.service.api.support.financeiro.credito;

import com.upsaude.api.response.financeiro.CreditoOrcamentarioResponse;
import com.upsaude.entity.financeiro.CreditoOrcamentario;
import com.upsaude.mapper.financeiro.CreditoOrcamentarioMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreditoOrcamentarioResponseBuilder {

    private final CreditoOrcamentarioMapper mapper;

    public CreditoOrcamentarioResponse build(CreditoOrcamentario entity) {
        return mapper.toResponse(entity);
    }
}

