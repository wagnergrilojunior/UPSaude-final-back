package com.upsaude.mapper.diagnostico;

import com.upsaude.api.response.diagnostico.Ciap2Response;
import com.upsaude.entity.referencia.Ciap2;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface Ciap2Mapper {

    Ciap2Response toResponse(Ciap2 entity);

    List<Ciap2Response> toResponseList(List<Ciap2> entities);
}
