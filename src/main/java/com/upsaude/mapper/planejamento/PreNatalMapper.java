package com.upsaude.mapper.planejamento;
import com.upsaude.api.response.paciente.ResponsavelLegalResponse;
import com.upsaude.api.response.paciente.PacienteResponse;

import com.upsaude.api.request.planejamento.PreNatalRequest;
import com.upsaude.api.response.planejamento.PreNatalResponse;
import com.upsaude.dto.PreNatalDTO;
import com.upsaude.entity.planejamento.PreNatal;
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

    // Evita ciclos/recursões indiretas via PacienteResponse/ResponsavelLegalResponse e grafos grandes.
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "profissionalResponsavel", ignore = true)
    @Mapping(target = "equipeSaude", ignore = true)
    PreNatalResponse toResponse(PreNatal entity);
}
