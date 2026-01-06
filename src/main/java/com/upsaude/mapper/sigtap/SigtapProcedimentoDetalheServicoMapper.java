package com.upsaude.mapper.sigtap;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.upsaude.api.response.referencia.sigtap.SigtapProcedimentoDetalheServicoResponse;
import com.upsaude.entity.referencia.sigtap.SigtapProcedimentoServico;

@Mapper(componentModel = "spring")
public interface SigtapProcedimentoDetalheServicoMapper {

    @Mapping(target = "codigoServico", source = "servicoClassificacao.servico.codigoOficial")
    @Mapping(target = "nomeServico", source = "servicoClassificacao.servico.nome")
    @Mapping(target = "codigoClassificacao", source = "servicoClassificacao.codigoClassificacao")
    @Mapping(target = "nomeClassificacao", source = "servicoClassificacao.nome")
    SigtapProcedimentoDetalheServicoResponse toResponse(SigtapProcedimentoServico entity);
}
