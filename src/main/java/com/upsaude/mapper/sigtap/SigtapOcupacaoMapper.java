package com.upsaude.mapper.sigtap;

import org.mapstruct.Mapper;

import com.upsaude.api.response.referencia.sigtap.SigtapOcupacaoResponse;
import com.upsaude.entity.referencia.sigtap.SigtapOcupacao;

@Mapper(componentModel = "spring")
public interface SigtapOcupacaoMapper {
    SigtapOcupacaoResponse toResponse(SigtapOcupacao entity);
}
