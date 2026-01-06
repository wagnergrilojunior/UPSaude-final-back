package com.upsaude.mapper.sigtap;

import org.mapstruct.Mapper;

import com.upsaude.api.response.referencia.sigtap.SigtapFinanciamentoResponse;
import com.upsaude.entity.referencia.sigtap.SigtapFinanciamento;

@Mapper(componentModel = "spring")
public interface SigtapFinanciamentoMapper {
    SigtapFinanciamentoResponse toResponse(SigtapFinanciamento entity);
}
