package com.upsaude.mapper.sigtap;

import org.mapstruct.Mapper;

import com.upsaude.api.response.referencia.sigtap.SigtapRenasesResponse;
import com.upsaude.entity.referencia.sigtap.SigtapRenases;

@Mapper(componentModel = "spring")
public interface SigtapRenasesMapper {
    SigtapRenasesResponse toResponse(SigtapRenases entity);
}
