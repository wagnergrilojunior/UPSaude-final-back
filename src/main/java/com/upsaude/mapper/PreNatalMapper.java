package com.upsaude.mapper;

import com.upsaude.api.request.PreNatalRequest;
import com.upsaude.api.response.PreNatalResponse;
import com.upsaude.dto.PreNatalDTO;
import com.upsaude.entity.PreNatal;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = {EquipeSaudeMapper.class, PacienteMapper.class, ProfissionaisSaudeMapper.class})
public interface PreNatalMapper extends EntityMapper<PreNatal, PreNatalDTO> {

    @Mapping(target = "active", ignore = true)
    PreNatal toEntity(PreNatalDTO dto);

    PreNatalDTO toDTO(PreNatal entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "equipeSaude", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "profissionalResponsavel", ignore = true)
    PreNatal fromRequest(PreNatalRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "equipeSaude", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "profissionalResponsavel", ignore = true)
    void updateFromRequest(PreNatalRequest request, @MappingTarget PreNatal entity);

    PreNatalResponse toResponse(PreNatal entity);
}
