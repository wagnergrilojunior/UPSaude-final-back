package com.upsaude.mapper.profissional;

import org.mapstruct.Mapper;

import com.upsaude.api.response.profissional.EspecialidadeResponse;
import com.upsaude.entity.referencia.sigtap.SigtapOcupacao;
import com.upsaude.mapper.config.MappingConfig;

@Mapper(config = MappingConfig.class)
public interface EspecialidadeMapper {
    EspecialidadeResponse toResponse(SigtapOcupacao entity);
}

