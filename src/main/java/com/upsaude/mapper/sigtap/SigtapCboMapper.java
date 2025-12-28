package com.upsaude.mapper.sigtap;

import org.mapstruct.Mapper;

import com.upsaude.api.response.referencia.sigtap.SigtapCboResponse;
import com.upsaude.entity.referencia.sigtap.SigtapOcupacao;

@Mapper(componentModel = "spring")
public interface SigtapCboMapper {
    SigtapCboResponse toResponse(SigtapOcupacao entity);
}

