package com.upsaude.service.support.deficiencias;

import com.upsaude.api.response.DeficienciasResponse;
import com.upsaude.entity.Deficiencias;
import com.upsaude.mapper.DeficienciasMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeficienciasResponseBuilder {

    private final DeficienciasMapper mapper;

    public DeficienciasResponse build(Deficiencias entity) {
        return mapper.toResponse(entity);
    }
}

