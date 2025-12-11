package com.upsaude.mapper;

import com.upsaude.api.request.EducacaoSaudeRequest;
import com.upsaude.api.response.EducacaoSaudeResponse;
import com.upsaude.dto.EducacaoSaudeDTO;
import com.upsaude.entity.EducacaoSaude;
import com.upsaude.entity.EquipeSaude;
import com.upsaude.entity.ProfissionaisSaude;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = {EquipeSaudeMapper.class, ProfissionaisSaudeMapper.class})
public interface EducacaoSaudeMapper extends EntityMapper<EducacaoSaude, EducacaoSaudeDTO> {

    @Mapping(target = "active", ignore = true)
    EducacaoSaude toEntity(EducacaoSaudeDTO dto);

    EducacaoSaudeDTO toDTO(EducacaoSaude entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "equipeSaude", ignore = true)
    @Mapping(target = "profissionalResponsavel", ignore = true)
    @Mapping(target = "participantes", ignore = true)
    @Mapping(target = "profissionaisParticipantes", ignore = true)
    EducacaoSaude fromRequest(EducacaoSaudeRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "equipeSaude", ignore = true)
    @Mapping(target = "profissionalResponsavel", ignore = true)
    @Mapping(target = "participantes", ignore = true)
    @Mapping(target = "profissionaisParticipantes", ignore = true)
    void updateFromRequest(EducacaoSaudeRequest request, @MappingTarget EducacaoSaude entity);

    EducacaoSaudeResponse toResponse(EducacaoSaude entity);
}
