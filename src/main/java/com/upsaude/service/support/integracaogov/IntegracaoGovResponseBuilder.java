package com.upsaude.service.support.integracaogov;

import org.springframework.stereotype.Service;

import com.upsaude.api.response.sistema.integracao.IntegracaoGovResponse;
import com.upsaude.entity.sistema.integracao.IntegracaoGov;
import com.upsaude.mapper.sistema.integracao.IntegracaoGovMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IntegracaoGovResponseBuilder {

    private final IntegracaoGovMapper mapper;

    public IntegracaoGovResponse build(IntegracaoGov entity) {
        return mapper.toResponse(entity);
    }
}
