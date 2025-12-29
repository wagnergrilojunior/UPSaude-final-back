package com.upsaude.mapper.estabelecimento;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.upsaude.api.request.estabelecimento.EstabelecimentosRequest;
import com.upsaude.api.response.estabelecimento.EstabelecimentosResponse;
import com.upsaude.entity.estabelecimento.Estabelecimentos;
import com.upsaude.mapper.config.MappingConfig;

@Mapper(config = MappingConfig.class)
public interface EstabelecimentosMapper {

    @Mapping(target = "active", ignore = true)
    Estabelecimentos toEntity(EstabelecimentosResponse dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "endereco", ignore = true)
    @Mapping(target = "equipamentos", ignore = true)
    Estabelecimentos fromRequest(EstabelecimentosRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "endereco", ignore = true)
    @Mapping(target = "equipamentos", ignore = true)
    void updateFromRequest(EstabelecimentosRequest request, @MappingTarget Estabelecimentos entity);

    EstabelecimentosResponse toResponse(Estabelecimentos entity);
}
