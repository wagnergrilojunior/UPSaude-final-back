package com.upsaude.mapper.referencia.cid;

import org.mapstruct.Mapper;

import com.upsaude.api.response.referencia.cid.Cid10CapituloResponse;
import com.upsaude.entity.referencia.cid.Cid10Capitulos;

@Mapper(componentModel = "spring")
public interface Cid10CapituloMapper {
    Cid10CapituloResponse toResponse(Cid10Capitulos entity);
}
