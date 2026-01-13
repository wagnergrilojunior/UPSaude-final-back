package com.upsaude.mapper.financeiro;

import com.upsaude.api.request.financeiro.ContaContabilRequest;
import com.upsaude.api.response.financeiro.ContaContabilResponse;
import com.upsaude.api.response.financeiro.ContaContabilSimplificadaResponse;
import com.upsaude.entity.financeiro.ContaContabil;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(config = MappingConfig.class, uses = { PlanoContasMapper.class })
public interface ContaContabilMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "planoContas", ignore = true)
    @Mapping(target = "contaPai", ignore = true)
    ContaContabil fromRequest(ContaContabilRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "planoContas", ignore = true)
    @Mapping(target = "contaPai", ignore = true)
    void updateFromRequest(ContaContabilRequest request, @MappingTarget ContaContabil entity);

    ContaContabilResponse toResponse(ContaContabil entity);

    @Named("toSimplifiedResponse")
    ContaContabilSimplificadaResponse toSimplifiedResponse(ContaContabil entity);
}

