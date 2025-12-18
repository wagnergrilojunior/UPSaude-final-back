package com.upsaude.mapper.sigtap;

import com.upsaude.dto.sigtap.SigtapGrupoResponse;
import com.upsaude.entity.sigtap.SigtapGrupo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SigtapGrupoMapper {
    SigtapGrupoResponse toResponse(SigtapGrupo entity);
}

