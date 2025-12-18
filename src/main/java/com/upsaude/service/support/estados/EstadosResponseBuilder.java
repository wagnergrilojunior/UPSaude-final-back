package com.upsaude.service.support.estados;

import org.springframework.stereotype.Service;

import com.upsaude.api.response.referencia.geografico.EstadosResponse;
import com.upsaude.entity.referencia.geografico.Estados;
import com.upsaude.mapper.referencia.geografico.EstadosMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EstadosResponseBuilder {

    private final EstadosMapper mapper;

    public EstadosResponse build(Estados entity) {
        return mapper.toResponse(entity);
    }
}

