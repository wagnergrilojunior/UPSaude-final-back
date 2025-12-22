package com.upsaude.mapper.sigtap;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.upsaude.api.response.referencia.sigtap.SigtapFormaOrganizacaoResponse;
import com.upsaude.entity.referencia.sigtap.SigtapFormaOrganizacao;

@Mapper(componentModel = "spring")
public interface SigtapFormaOrganizacaoMapper {

    @Mapping(target = "subgrupoCodigo", source = "subgrupo.codigoOficial")
    @Mapping(target = "subgrupoNome", source = "subgrupo.nome")
    @Mapping(target = "grupoCodigo", source = "subgrupo.grupo.codigoOficial")
    @Mapping(target = "grupoNome", source = "subgrupo.grupo.nome")
    SigtapFormaOrganizacaoResponse toResponse(SigtapFormaOrganizacao entity);
}
