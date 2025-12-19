package com.upsaude.mapper.sigtap;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.upsaude.dto.referencia.sigtap.SigtapSubgrupoResponse;
import com.upsaude.entity.referencia.sigtap.SigtapSubgrupo;

@Mapper(componentModel = "spring")
public interface SigtapSubgrupoMapper {

    @Mapping(target = "grupoCodigo", source = "grupo.codigoOficial")
    @Mapping(target = "grupoNome", source = "grupo.nome")
    SigtapSubgrupoResponse toResponse(SigtapSubgrupo entity);
}
