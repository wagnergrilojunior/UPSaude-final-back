package com.upsaude.mapper.sigtap;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.upsaude.api.response.referencia.sigtap.SigtapProcedimentoDetalheRegistroResponse;
import com.upsaude.entity.referencia.sigtap.SigtapProcedimentoRegistro;

@Mapper(componentModel = "spring")
public interface SigtapProcedimentoDetalheRegistroMapper {

    @Mapping(target = "codigoRegistro", source = "registro.codigoOficial")
    @Mapping(target = "nomeRegistro", source = "registro.nome")
    @Mapping(target = "competenciaInicial", source = "competenciaInicial")
    @Mapping(target = "competenciaFinal", source = "competenciaFinal")
    SigtapProcedimentoDetalheRegistroResponse toResponse(SigtapProcedimentoRegistro entity);
}
