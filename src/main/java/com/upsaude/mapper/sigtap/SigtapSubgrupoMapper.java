package com.upsaude.mapper.sigtap;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.upsaude.api.response.referencia.sigtap.SigtapSubgrupoResponse;
import com.upsaude.entity.referencia.sigtap.SigtapSubgrupo;

@Mapper(componentModel = "spring")
public interface SigtapSubgrupoMapper {

    @Mapping(target = "grupoCodigo", source = "grupo.codigoOficial")
    @Mapping(target = "grupoNome", source = "grupo.nome")
    SigtapSubgrupoResponse toResponse(SigtapSubgrupo entity);
}
