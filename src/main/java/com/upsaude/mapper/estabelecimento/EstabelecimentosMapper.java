package com.upsaude.mapper.estabelecimento;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.upsaude.api.request.estabelecimento.EstabelecimentosRequest;
import com.upsaude.api.response.estabelecimento.EstabelecimentosResponse;
import com.upsaude.entity.estabelecimento.Estabelecimentos;
import com.upsaude.mapper.config.MappingConfig;
import com.upsaude.mapper.geral.EnderecoMapper;
import com.upsaude.mapper.profissional.ProfissionaisSaudeMapper;

@Mapper(config = MappingConfig.class, uses = {EnderecoMapper.class, ProfissionaisSaudeMapper.class})
public interface EstabelecimentosMapper {

    @Mapping(target = "active", ignore = true)
    Estabelecimentos toEntity(EstabelecimentosResponse dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "enderecoPrincipal", ignore = true)
    @Mapping(target = "responsavelAdministrativo", ignore = true)
    @Mapping(target = "responsavelTecnico", ignore = true)
    @Mapping(target = "enderecosSecundarios", ignore = true)
    @Mapping(target = "servicos", ignore = true)
    @Mapping(target = "equipamentos", ignore = true)
    @Mapping(target = "infraestrutura", ignore = true)
    @Mapping(target = "equipes", ignore = true)
    Estabelecimentos fromRequest(EstabelecimentosRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "enderecoPrincipal", ignore = true)
    @Mapping(target = "responsavelAdministrativo", ignore = true)
    @Mapping(target = "responsavelTecnico", ignore = true)
    @Mapping(target = "enderecosSecundarios", ignore = true)
    @Mapping(target = "servicos", ignore = true)
    @Mapping(target = "equipamentos", ignore = true)
    @Mapping(target = "infraestrutura", ignore = true)
    @Mapping(target = "equipes", ignore = true)
    void updateFromRequest(EstabelecimentosRequest request, @MappingTarget Estabelecimentos entity);

    EstabelecimentosResponse toResponse(Estabelecimentos entity);
}
