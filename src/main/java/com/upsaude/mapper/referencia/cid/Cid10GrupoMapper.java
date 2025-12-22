package com.upsaude.mapper.referencia.cid;

import org.mapstruct.Mapper;

import com.upsaude.api.response.referencia.cid.Cid10GrupoResponse;
import com.upsaude.entity.referencia.cid.Cid10Grupos;

@Mapper(componentModel = "spring")
public interface Cid10GrupoMapper {
    Cid10GrupoResponse toResponse(Cid10Grupos entity);
}
