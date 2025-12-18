package com.upsaude.mapper.sigtap;

import org.mapstruct.Mapper;

import com.upsaude.dto.referencia.sigtap.SigtapGrupoResponse;
import com.upsaude.entity.referencia.sigtap.SigtapGrupo;

@Mapper(componentModel = "spring")
public interface SigtapGrupoMapper {
    SigtapGrupoResponse toResponse(SigtapGrupo entity);
}

