package com.upsaude.mapper.sigtap;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.upsaude.api.response.referencia.sigtap.SigtapProcedimentoResponse;
import com.upsaude.entity.referencia.sigtap.SigtapProcedimento;

@Mapper(componentModel = "spring")
public interface SigtapProcedimentoMapper {

    @Mapping(target = "grupoCodigo", source = "formaOrganizacao.subgrupo.grupo.codigoOficial")
    @Mapping(target = "grupoNome", source = "formaOrganizacao.subgrupo.grupo.nome")
    @Mapping(target = "subgrupoCodigo", source = "formaOrganizacao.subgrupo.codigoOficial")
    @Mapping(target = "subgrupoNome", source = "formaOrganizacao.subgrupo.nome")
    @Mapping(target = "formaOrganizacaoCodigo", source = "formaOrganizacao.codigoOficial")
    @Mapping(target = "formaOrganizacaoNome", source = "formaOrganizacao.nome")
    SigtapProcedimentoResponse toResponse(SigtapProcedimento entity);
}

