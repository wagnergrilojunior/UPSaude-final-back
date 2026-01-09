package com.upsaude.mapper.sigtap;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.upsaude.api.response.referencia.sigtap.SigtapProcedimentoDetalheResponse;
import com.upsaude.entity.referencia.sigtap.SigtapProcedimentoDetalhe;

@Mapper(componentModel = "spring")
public interface SigtapProcedimentoDetalheMapper {

    @Mapping(target = "procedimentoId", source = "procedimento.id")
    SigtapProcedimentoDetalheResponse toResponse(SigtapProcedimentoDetalhe entity);
}
