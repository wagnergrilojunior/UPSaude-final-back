package com.upsaude.mapper;

import com.upsaude.api.request.ProfissionaisSaudeRequest;
import com.upsaude.api.response.ProfissionaisSaudeResponse;
import com.upsaude.dto.ProfissionaisSaudeDTO;
import com.upsaude.entity.ProfissionaisSaude;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MappingConfig.class)
public interface ProfissionaisSaudeMapper extends EntityMapper<ProfissionaisSaude, ProfissionaisSaudeDTO> {

    @Mapping(target = "tenant", ignore = true)
    ProfissionaisSaude toEntity(ProfissionaisSaudeDTO dto);

    ProfissionaisSaudeDTO toDTO(ProfissionaisSaude entity);

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "active", ignore = true)
    ProfissionaisSaude fromRequest(ProfissionaisSaudeRequest request);

    ProfissionaisSaudeResponse toResponse(ProfissionaisSaude entity);
}

