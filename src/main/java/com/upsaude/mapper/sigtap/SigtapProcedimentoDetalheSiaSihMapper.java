package com.upsaude.mapper.sigtap;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.upsaude.api.response.referencia.sigtap.SigtapProcedimentoDetalheOrigemResponse;
import com.upsaude.entity.referencia.sigtap.SigtapProcedimentoSiaSih;

@Mapper(componentModel = "spring")
public interface SigtapProcedimentoDetalheSiaSihMapper {

    @Mapping(target = "tipo", constant = "SIA/SIH")
    @Mapping(target = "tipoProcedimento", source = "tipoProcedimento")
    @Mapping(target = "codigoProcedimentoOrigem", source = "siaSih.codigoOficial")
    @Mapping(target = "nomeProcedimentoOrigem", source = "siaSih.nome")
    SigtapProcedimentoDetalheOrigemResponse toResponse(SigtapProcedimentoSiaSih entity);
}
