package com.upsaude.mapper.sigtap;

import org.mapstruct.Mapper;

import com.upsaude.api.response.referencia.sigtap.SigtapHabilitacaoResponse;
import com.upsaude.entity.referencia.sigtap.SigtapHabilitacao;

@Mapper(componentModel = "spring")
public interface SigtapHabilitacaoMapper {
    SigtapHabilitacaoResponse toResponse(SigtapHabilitacao entity);
}
