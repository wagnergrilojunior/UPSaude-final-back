package com.upsaude.mapper.sigtap;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.upsaude.api.response.referencia.sigtap.SigtapProcedimentoDetalheTussResponse;
import com.upsaude.entity.referencia.sigtap.SigtapProcedimentoTuss;

@Mapper(componentModel = "spring")
public interface SigtapProcedimentoDetalheTussMapper {

    @Mapping(target = "codigoTuss", source = "tuss.codigoOficial")
    @Mapping(target = "nomeTuss", source = "tuss.nome")
    SigtapProcedimentoDetalheTussResponse toResponse(SigtapProcedimentoTuss entity);
}
