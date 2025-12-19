package com.upsaude.mapper.sigtap;

import org.mapstruct.Mapper;

import com.upsaude.dto.referencia.sigtap.SigtapTussResponse;
import com.upsaude.entity.referencia.sigtap.SigtapTuss;

@Mapper(componentModel = "spring")
public interface SigtapTussMapper {
    SigtapTussResponse toResponse(SigtapTuss entity);
}
