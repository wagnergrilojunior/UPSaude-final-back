package com.upsaude.mapper.profissional;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.upsaude.api.request.profissional.ProfissionaisSaudeRequest;
import com.upsaude.api.response.profissional.ProfissionaisSaudeResponse;
import com.upsaude.dto.profissional.ProfissionaisSaudeDTO;
import com.upsaude.entity.profissional.ProfissionaisSaude;
import com.upsaude.mapper.EntityMapper;
import com.upsaude.mapper.config.MappingConfig;
import com.upsaude.mapper.geral.EnderecoMapper;

@Mapper(config = MappingConfig.class, uses = {ConselhosProfissionaisMapper.class, EnderecoMapper.class})
public interface ProfissionaisSaudeMapper extends EntityMapper<ProfissionaisSaude, ProfissionaisSaudeDTO> {

    @Mapping(target = "active", ignore = true)
    ProfissionaisSaude toEntity(ProfissionaisSaudeDTO dto);

    ProfissionaisSaudeDTO toDTO(ProfissionaisSaude entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "conselho", ignore = true)
    @Mapping(target = "enderecoProfissional", ignore = true)
    @Mapping(target = "especialidades", ignore = true)
    @Mapping(target = "historicoHabilitacao", ignore = true)
    ProfissionaisSaude fromRequest(ProfissionaisSaudeRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "conselho", ignore = true)
    @Mapping(target = "enderecoProfissional", ignore = true)
    @Mapping(target = "especialidades", ignore = true)
    @Mapping(target = "historicoHabilitacao", ignore = true)
    void updateFromRequest(ProfissionaisSaudeRequest request, @MappingTarget ProfissionaisSaude entity);

    ProfissionaisSaudeResponse toResponse(ProfissionaisSaude entity);
}
