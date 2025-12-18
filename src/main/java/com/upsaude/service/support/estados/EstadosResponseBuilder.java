package com.upsaude.service.support.estados;

import com.upsaude.api.response.geografico.EstadosResponse;
import com.upsaude.entity.geografico.Estados;
import com.upsaude.mapper.EstadosMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EstadosResponseBuilder {

    private final EstadosMapper mapper;

    public EstadosResponse build(Estados entity) {
        return mapper.toResponse(entity);
    }
}

