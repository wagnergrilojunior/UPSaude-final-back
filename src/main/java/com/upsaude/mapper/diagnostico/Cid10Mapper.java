package com.upsaude.mapper.diagnostico;

import com.upsaude.api.response.diagnostico.Cid10Response;
import com.upsaude.entity.referencia.cid.Cid10Subcategorias;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface Cid10Mapper {

    @Mapping(target = "ativo", source = "active")
    Cid10Response toResponse(Cid10Subcategorias entity);

    List<Cid10Response> toResponseList(List<Cid10Subcategorias> entities);
}
