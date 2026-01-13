package com.upsaude.service.api.support.financeiro.contacontabil;

import com.upsaude.api.response.financeiro.ContaContabilResponse;
import com.upsaude.entity.financeiro.ContaContabil;
import com.upsaude.mapper.financeiro.ContaContabilMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContaContabilResponseBuilder {

    private final ContaContabilMapper mapper;

    public ContaContabilResponse build(ContaContabil entity) {
        return mapper.toResponse(entity);
    }
}

