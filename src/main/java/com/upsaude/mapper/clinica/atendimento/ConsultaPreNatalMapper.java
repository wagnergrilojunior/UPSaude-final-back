package com.upsaude.mapper.clinica.atendimento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.upsaude.api.request.clinica.atendimento.ConsultaPreNatalRequest;
import com.upsaude.api.response.clinica.atendimento.ConsultaPreNatalResponse;
import com.upsaude.dto.clinica.atendimento.ConsultaPreNatalDTO;
import com.upsaude.entity.clinica.atendimento.ConsultaPreNatal;
import com.upsaude.mapper.EntityMapper;
import com.upsaude.mapper.config.MappingConfig;
import com.upsaude.mapper.profissional.ProfissionaisSaudeMapper;
import com.upsaude.mapper.saude_publica.planejamento.PreNatalMapper;

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

    @Mapping(target = "preNatal", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    ConsultaPreNatalResponse toResponse(ConsultaPreNatal entity);
}
