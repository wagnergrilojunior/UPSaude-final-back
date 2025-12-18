package com.upsaude.service.support.convenio;

import org.hibernate.Hibernate;
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
        if (entity != null) {
            if (entity.getEndereco() != null) Hibernate.initialize(entity.getEndereco());
        }
        return mapper.toResponse(entity);
    }
}
