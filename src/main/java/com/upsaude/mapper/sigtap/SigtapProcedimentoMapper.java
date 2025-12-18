package com.upsaude.mapper.sigtap;

import com.upsaude.dto.sigtap.SigtapProcedimentoResponse;
import com.upsaude.entity.sigtap.SigtapProcedimento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper (MapStruct) para expor Procedimentos SIGTAP via REST interno.
 *
 * <p>Observa??o: usa mapeamentos encadeados (forma -> subgrupo -> grupo) com null-safety
 * gerada automaticamente pelo MapStruct.
 */
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

