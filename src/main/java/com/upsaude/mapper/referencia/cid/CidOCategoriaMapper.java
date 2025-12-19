package com.upsaude.mapper.referencia.cid;

import org.mapstruct.Mapper;

import com.upsaude.dto.referencia.cid.CidOCategoriaResponse;
import com.upsaude.entity.referencia.cid.CidOCategorias;

@Mapper(componentModel = "spring")
public interface CidOCategoriaMapper {
    CidOCategoriaResponse toResponse(CidOCategorias entity);
}
