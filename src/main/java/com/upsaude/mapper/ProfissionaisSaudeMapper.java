package com.upsaude.mapper;

import com.upsaude.api.request.ProfissionaisSaudeRequest;
import com.upsaude.api.response.ProfissionaisSaudeResponse;
import com.upsaude.dto.ProfissionaisSaudeDTO;
import com.upsaude.entity.ProfissionaisSaude;
import com.upsaude.entity.ConselhosProfissionais;
import com.upsaude.entity.Endereco;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

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
