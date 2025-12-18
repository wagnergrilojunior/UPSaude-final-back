package com.upsaude.mapper.sigtap;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.upsaude.dto.referencia.sigtap.SigtapCompatibilidadeResponse;
import com.upsaude.entity.referencia.sigtap.SigtapCompatibilidade;

@Mapper(componentModel = "spring")
public interface SigtapCompatibilidadeMapper {

    @Mapping(target = "codigoCompatibilidadePossivel", source = "compatibilidadePossivel.codigoOficial")
    @Mapping(target = "tipoCompatibilidade", source = "compatibilidadePossivel.tipoCompatibilidade")
    @Mapping(target = "codigoProcedimentoPrincipal", source = "procedimentoPrincipal.codigoOficial")
    @Mapping(target = "nomeProcedimentoPrincipal", source = "procedimentoPrincipal.nome")
    @Mapping(target = "codigoProcedimentoSecundario", source = "procedimentoSecundario.codigoOficial")
    @Mapping(target = "nomeProcedimentoSecundario", source = "procedimentoSecundario.nome")
    SigtapCompatibilidadeResponse toResponse(SigtapCompatibilidade entity);
}

