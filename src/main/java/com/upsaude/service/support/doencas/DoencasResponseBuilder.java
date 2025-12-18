package com.upsaude.service.support.doencas;

import org.springframework.stereotype.Service;

import com.upsaude.api.response.clinica.doencas.DoencasResponse;
import com.upsaude.entity.clinica.doencas.Doencas;
import com.upsaude.mapper.clinica.doencas.DoencasMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DoencasResponseBuilder {

    private final DoencasMapper mapper;

    public DoencasResponse build(Doencas entity) {
        return mapper.toResponse(entity);
    }
}

