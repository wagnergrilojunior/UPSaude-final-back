package com.upsaude.mapper.financeiro;

import com.upsaude.api.request.financeiro.RegraClassificacaoContabilRequest;
import com.upsaude.api.response.financeiro.RegraClassificacaoContabilResponse;
import com.upsaude.entity.financeiro.RegraClassificacaoContabil;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = { ContaContabilMapper.class })
public interface RegraClassificacaoContabilMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", source = "ativo")
    @Mapping(target = "contaContabilDestino", ignore = true)
    RegraClassificacaoContabil fromRequest(RegraClassificacaoContabilRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", source = "ativo")
    @Mapping(target = "contaContabilDestino", ignore = true)
    void updateFromRequest(RegraClassificacaoContabilRequest request, @MappingTarget RegraClassificacaoContabil entity);

    @Mapping(target = "ativo", source = "active")
    RegraClassificacaoContabilResponse toResponse(RegraClassificacaoContabil entity);
}

