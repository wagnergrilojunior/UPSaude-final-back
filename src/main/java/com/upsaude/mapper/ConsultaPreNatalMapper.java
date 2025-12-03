package com.upsaude.mapper;

import com.upsaude.api.request.ConsultaPreNatalRequest;
import com.upsaude.api.response.ConsultaPreNatalResponse;
import com.upsaude.dto.ConsultaPreNatalDTO;
import com.upsaude.entity.ConsultaPreNatal;
import com.upsaude.entity.PreNatal;
import com.upsaude.entity.ProfissionaisSaude;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para conversões de ConsultaPreNatal.
 * Entity ↔ DTO ↔ Request/Response
 */
@Mapper(config = MappingConfig.class, uses = {PreNatalMapper.class, ProfissionaisSaudeMapper.class})
public interface ConsultaPreNatalMapper extends EntityMapper<ConsultaPreNatal, ConsultaPreNatalDTO> {

    /**
     * Converte DTO para Entity.
     * O campo 'active' é ignorado (gerenciado pelo sistema).
     */
    @Mapping(target = "active", ignore = true)
    ConsultaPreNatal toEntity(ConsultaPreNatalDTO dto);

    /**
     * Converte Entity para DTO.
     */
    ConsultaPreNatalDTO toDTO(ConsultaPreNatal entity);

    /**
     * Converte Request para Entity.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "preNatal", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    ConsultaPreNatal fromRequest(ConsultaPreNatalRequest request);

    /**
     * Atualiza Entity existente com dados do Request.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "preNatal", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    void updateFromRequest(ConsultaPreNatalRequest request, @MappingTarget ConsultaPreNatal entity);

    /**
     * Converte Entity para Response.
     */
    ConsultaPreNatalResponse toResponse(ConsultaPreNatal entity);
}
