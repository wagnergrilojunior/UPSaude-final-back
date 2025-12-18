package com.upsaude.mapper.sigtap;

import com.upsaude.dto.sigtap.SigtapProcedimentoDetalheResponse;
import com.upsaude.entity.sigtap.SigtapProcedimentoDetalhe;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper (MapStruct) para detalhe do procedimento SIGTAP.
 */
@Mapper(componentModel = "spring")
public interface SigtapProcedimentoDetalheMapper {

    @Mapping(target = "procedimentoId", source = "procedimento.id")
    SigtapProcedimentoDetalheResponse toResponse(SigtapProcedimentoDetalhe entity);
}

