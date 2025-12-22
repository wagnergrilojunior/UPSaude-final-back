package com.upsaude.mapper.sigtap;

import org.mapstruct.Mapper;

import com.upsaude.api.response.referencia.sigtap.SigtapServicoResponse;
import com.upsaude.entity.referencia.sigtap.SigtapServico;

@Mapper(componentModel = "spring")
public interface SigtapServicoMapper {
    SigtapServicoResponse toResponse(SigtapServico entity);
}
