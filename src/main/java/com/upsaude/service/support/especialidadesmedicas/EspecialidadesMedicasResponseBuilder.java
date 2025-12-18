package com.upsaude.service.support.especialidadesmedicas;

import com.upsaude.api.response.profissional.EspecialidadesMedicasResponse;
import com.upsaude.entity.profissional.EspecialidadesMedicas;
import com.upsaude.mapper.EspecialidadesMedicasMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EspecialidadesMedicasResponseBuilder {

    private final EspecialidadesMedicasMapper mapper;

    public EspecialidadesMedicasResponse build(EspecialidadesMedicas entity) {
        return mapper.toResponse(entity);
    }
}

