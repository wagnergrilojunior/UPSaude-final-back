package com.upsaude.mapper.sigtap;

import org.mapstruct.Mapper;

import com.upsaude.api.response.referencia.sigtap.SigtapRubricaResponse;
import com.upsaude.entity.referencia.sigtap.SigtapRubrica;

@Mapper(componentModel = "spring")
public interface SigtapRubricaMapper {
    SigtapRubricaResponse toResponse(SigtapRubrica entity);
}

