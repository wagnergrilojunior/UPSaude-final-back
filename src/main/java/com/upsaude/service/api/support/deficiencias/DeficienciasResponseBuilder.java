package com.upsaude.service.api.support.deficiencias;

import com.upsaude.api.response.deficiencia.DeficienciasResponse;
import com.upsaude.entity.paciente.deficiencia.Deficiencias;
import com.upsaude.mapper.paciente.deficiencia.DeficienciasMapper;
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

