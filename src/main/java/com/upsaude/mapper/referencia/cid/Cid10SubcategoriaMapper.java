package com.upsaude.mapper.referencia.cid;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.upsaude.dto.referencia.cid.Cid10SubcategoriaResponse;
import com.upsaude.entity.referencia.cid.Cid10Subcategorias;

@Mapper(componentModel = "spring")
public interface Cid10SubcategoriaMapper {

    @Mapping(target = "categoriaCat", source = "categoriaCat")
    @Mapping(target = "categoriaDescricao", source = "categoria.descricao")
    Cid10SubcategoriaResponse toResponse(Cid10Subcategorias entity);
}
