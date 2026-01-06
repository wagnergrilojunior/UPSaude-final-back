package com.upsaude.mapper.sigtap;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.upsaude.api.response.referencia.sigtap.SigtapProcedimentoDetalheCidResponse;
import com.upsaude.entity.referencia.sigtap.SigtapProcedimentoCid;

@Mapper(componentModel = "spring")
public interface SigtapProcedimentoDetalheCidMapper {

    @Mapping(target = "codigoCid", source = "cid10Subcategoria.subcat")
    @Mapping(target = "nomeCid", source = "cid10Subcategoria.descricao")
    SigtapProcedimentoDetalheCidResponse toResponse(SigtapProcedimentoCid entity);
}
