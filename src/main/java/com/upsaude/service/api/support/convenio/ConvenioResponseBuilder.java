package com.upsaude.service.api.support.convenio;

import org.springframework.stereotype.Service;

import com.upsaude.api.response.convenio.ConvenioResponse;
import com.upsaude.entity.convenio.Convenio;
import com.upsaude.mapper.convenio.ConvenioMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConvenioResponseBuilder {

    private final ConvenioMapper mapper;

    public ConvenioResponse build(Convenio entity) {
        return mapper.toResponse(entity);
    }
}
