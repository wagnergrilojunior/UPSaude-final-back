package com.upsaude.service.support.especialidadesmedicas;

import org.springframework.stereotype.Service;

import com.upsaude.api.response.profissional.EspecialidadesMedicasResponse;
import com.upsaude.entity.profissional.EspecialidadesMedicas;
import com.upsaude.mapper.profissional.EspecialidadesMedicasMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EspecialidadesMedicasResponseBuilder {

    private final EspecialidadesMedicasMapper mapper;

    public EspecialidadesMedicasResponse build(EspecialidadesMedicas entity) {
        return mapper.toResponse(entity);
    }
}

