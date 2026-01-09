package com.upsaude.mapper.referencia.cid;

import org.mapstruct.Mapper;

import com.upsaude.api.response.referencia.cid.CidOGrupoResponse;
import com.upsaude.entity.referencia.cid.CidOGrupos;

@Mapper(componentModel = "spring")
public interface CidOGrupoMapper {
    CidOGrupoResponse toResponse(CidOGrupos entity);
}
