package com.upsaude.mapper.sigtap;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.upsaude.api.response.referencia.sigtap.SigtapProcedimentoDetalheOrigemResponse;
import com.upsaude.entity.referencia.sigtap.SigtapProcedimentoOrigem;

@Mapper(componentModel = "spring")
public interface SigtapProcedimentoDetalheOrigemMapper {

    @Mapping(target = "tipo", constant = "SIGTAP")
    @Mapping(target = "tipoProcedimento", ignore = true)
    @Mapping(target = "codigoProcedimentoOrigem", source = "procedimentoOrigem.codigoOficial")
    @Mapping(target = "nomeProcedimentoOrigem", source = "procedimentoOrigem.nome")
    SigtapProcedimentoDetalheOrigemResponse toResponse(SigtapProcedimentoOrigem entity);
}
