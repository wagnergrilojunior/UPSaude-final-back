package com.upsaude.mapper.sigtap;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.upsaude.api.response.referencia.sigtap.SigtapProcedimentoDetalheCboResponse;
import com.upsaude.entity.referencia.sigtap.SigtapProcedimentoOcupacao;

@Mapper(componentModel = "spring")
public interface SigtapProcedimentoDetalheCboMapper {
    
    @Mapping(target = "codigoCbo", source = "ocupacao.codigoOficial")
    @Mapping(target = "nomeCbo", source = "ocupacao.nome")
    SigtapProcedimentoDetalheCboResponse toResponse(SigtapProcedimentoOcupacao entity);
}
