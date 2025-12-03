package com.upsaude.mapper;

import com.upsaude.api.request.LogsAuditoriaRequest;
import com.upsaude.api.response.LogsAuditoriaResponse;
import com.upsaude.dto.LogsAuditoriaDTO;
import com.upsaude.entity.LogsAuditoria;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para conversões de LogsAuditoria.
 * Entity ↔ DTO ↔ Request/Response
 */
@Mapper(config = MappingConfig.class)
public interface LogsAuditoriaMapper extends EntityMapper<LogsAuditoria, LogsAuditoriaDTO> {

    /**
     * Converte DTO para Entity.
     * O campo 'active' é ignorado (gerenciado pelo sistema).
     */
    @Mapping(target = "active", ignore = true)
    LogsAuditoria toEntity(LogsAuditoriaDTO dto);

    /**
     * Converte Entity para DTO.
     */
    LogsAuditoriaDTO toDTO(LogsAuditoria entity);

    /**
     * Converte Request para Entity.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    LogsAuditoria fromRequest(LogsAuditoriaRequest request);

    /**
     * Atualiza Entity existente com dados do Request.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    void updateFromRequest(LogsAuditoriaRequest request, @MappingTarget LogsAuditoria entity);

    /**
     * Converte Entity para Response.
     */
    LogsAuditoriaResponse toResponse(LogsAuditoria entity);
}
