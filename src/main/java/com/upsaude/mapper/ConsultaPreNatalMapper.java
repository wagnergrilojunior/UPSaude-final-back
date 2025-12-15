package com.upsaude.mapper;

import com.upsaude.api.request.ConsultaPreNatalRequest;
import com.upsaude.api.response.ConsultaPreNatalResponse;
import com.upsaude.dto.ConsultaPreNatalDTO;
import com.upsaude.entity.ConsultaPreNatal;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = {PreNatalMapper.class, ProfissionaisSaudeMapper.class})
public interface ConsultaPreNatalMapper extends EntityMapper<ConsultaPreNatal, ConsultaPreNatalDTO> {

    @Mapping(target = "active", ignore = true)
    ConsultaPreNatal toEntity(ConsultaPreNatalDTO dto);

    ConsultaPreNatalDTO toDTO(ConsultaPreNatal entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "preNatal", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    ConsultaPreNatal fromRequest(ConsultaPreNatalRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "preNatal", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    void updateFromRequest(ConsultaPreNatalRequest request, @MappingTarget ConsultaPreNatal entity);

    // Evita ciclos/recursões via PreNatalResponse/PacienteResponse e grafos grandes.
    @Mapping(target = "preNatal", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    ConsultaPreNatalResponse toResponse(ConsultaPreNatal entity);
}
