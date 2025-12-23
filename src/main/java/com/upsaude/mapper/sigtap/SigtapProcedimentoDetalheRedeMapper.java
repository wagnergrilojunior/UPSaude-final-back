package com.upsaude.mapper.sigtap;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.upsaude.api.response.referencia.sigtap.SigtapProcedimentoDetalheRedeResponse;
import com.upsaude.entity.referencia.sigtap.SigtapProcedimentoComponenteRede;

@Mapper(componentModel = "spring")
public interface SigtapProcedimentoDetalheRedeMapper {
    
    @Mapping(target = "codigoRede", source = "componenteRede.codigoOficial")
    @Mapping(target = "nomeRede", source = "componenteRede.nome")
    SigtapProcedimentoDetalheRedeResponse toResponse(SigtapProcedimentoComponenteRede entity);
}
