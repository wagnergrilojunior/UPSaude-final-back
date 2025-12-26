package com.upsaude.mapper.sigtap;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.upsaude.api.response.referencia.sigtap.SigtapProcedimentoDetalheModalidadeResponse;
import com.upsaude.entity.referencia.sigtap.SigtapProcedimentoModalidade;

@Mapper(componentModel = "spring")
public interface SigtapProcedimentoDetalheModalidadeMapper {
    
    @Mapping(target = "codigoModalidade", source = "modalidade.codigoOficial")
    @Mapping(target = "nomeModalidade", source = "modalidade.nome")
    @Mapping(target = "competenciaInicial", source = "competenciaInicial")
    @Mapping(target = "competenciaFinal", source = "competenciaFinal")
    SigtapProcedimentoDetalheModalidadeResponse toResponse(SigtapProcedimentoModalidade entity);
}

