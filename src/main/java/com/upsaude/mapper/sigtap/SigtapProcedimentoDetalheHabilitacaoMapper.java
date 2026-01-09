package com.upsaude.mapper.sigtap;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.upsaude.api.response.referencia.sigtap.SigtapProcedimentoDetalheHabilitacaoResponse;
import com.upsaude.entity.referencia.sigtap.SigtapProcedimentoHabilitacao;

@Mapper(componentModel = "spring")
public interface SigtapProcedimentoDetalheHabilitacaoMapper {

    @Mapping(target = "codigoHabilitacao", source = "habilitacao.codigoOficial")
    @Mapping(target = "nomeHabilitacao", source = "habilitacao.nome")
    @Mapping(target = "codigoGrupoHabilitacao", source = "grupoHabilitacao.codigoOficial")
    @Mapping(target = "nomeGrupoHabilitacao", source = "grupoHabilitacao.nome")
    SigtapProcedimentoDetalheHabilitacaoResponse toResponse(SigtapProcedimentoHabilitacao entity);
}
