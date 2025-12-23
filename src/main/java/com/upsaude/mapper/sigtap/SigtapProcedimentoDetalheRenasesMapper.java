package com.upsaude.mapper.sigtap;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.upsaude.api.response.referencia.sigtap.SigtapProcedimentoDetalheRenasesResponse;
import com.upsaude.entity.referencia.sigtap.SigtapProcedimentoRenases;

@Mapper(componentModel = "spring")
public interface SigtapProcedimentoDetalheRenasesMapper {
    
    @Mapping(target = "codigoRenases", source = "renases.codigoOficial")
    @Mapping(target = "nomeRenases", source = "renases.nome")
    SigtapProcedimentoDetalheRenasesResponse toResponse(SigtapProcedimentoRenases entity);
}
