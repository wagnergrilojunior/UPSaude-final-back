package com.upsaude.mapper;

import com.upsaude.api.request.EstabelecimentosRequest;
import com.upsaude.api.response.EstabelecimentosResponse;
import com.upsaude.dto.EstabelecimentosDTO;
import com.upsaude.entity.Estabelecimentos;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = {EnderecoMapper.class, ProfissionaisSaudeMapper.class})
public interface EstabelecimentosMapper extends EntityMapper<Estabelecimentos, EstabelecimentosDTO> {

    @Mapping(target = "active", ignore = true)
    Estabelecimentos toEntity(EstabelecimentosDTO dto);

    EstabelecimentosDTO toDTO(Estabelecimentos entity);

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
