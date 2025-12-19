package com.upsaude.mapper.sigtap;

import org.mapstruct.Mapper;

import com.upsaude.dto.referencia.sigtap.SigtapModalidadeResponse;
import com.upsaude.entity.referencia.sigtap.SigtapModalidade;

@Mapper(componentModel = "spring")
public interface SigtapModalidadeMapper {
    SigtapModalidadeResponse toResponse(SigtapModalidade entity);
}
