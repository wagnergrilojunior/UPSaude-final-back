package com.upsaude.service.support.convenio;

import com.upsaude.api.response.ConvenioResponse;
import com.upsaude.entity.Convenio;
import com.upsaude.mapper.ConvenioMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConvenioResponseBuilder {

    private final ConvenioMapper mapper;

    public ConvenioResponse build(Convenio entity) {
        return mapper.toResponse(entity);
    }
}

