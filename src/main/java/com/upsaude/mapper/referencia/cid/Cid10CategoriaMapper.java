package com.upsaude.mapper.referencia.cid;

import org.mapstruct.Mapper;

import com.upsaude.api.response.referencia.cid.Cid10CategoriaResponse;
import com.upsaude.entity.referencia.cid.Cid10Categorias;

@Mapper(componentModel = "spring")
public interface Cid10CategoriaMapper {
    Cid10CategoriaResponse toResponse(Cid10Categorias entity);
}
