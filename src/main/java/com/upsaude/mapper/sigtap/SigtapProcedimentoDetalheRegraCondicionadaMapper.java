package com.upsaude.mapper.sigtap;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.upsaude.api.response.referencia.sigtap.SigtapProcedimentoDetalheRegraCondicionadaResponse;
import com.upsaude.entity.referencia.sigtap.SigtapProcedimentoRegraCondicionada;

@Mapper(componentModel = "spring")
public interface SigtapProcedimentoDetalheRegraCondicionadaMapper {

    @Mapping(target = "codigoRegra", source = "regraCondicionada.codigoOficial")
    @Mapping(target = "nomeRegra", source = "regraCondicionada.nome")
    SigtapProcedimentoDetalheRegraCondicionadaResponse toResponse(SigtapProcedimentoRegraCondicionada entity);
}
