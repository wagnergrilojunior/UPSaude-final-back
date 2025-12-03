package com.upsaude.mapper;

import com.upsaude.api.request.EstadosRequest;
import com.upsaude.api.response.EstadosResponse;
import com.upsaude.dto.EstadosDTO;
import com.upsaude.entity.Estados;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para conversões de Estados.
 * Entity ↔ DTO ↔ Request/Response
 */
@Mapper(config = MappingConfig.class)
public interface EstadosMapper extends EntityMapper<Estados, EstadosDTO> {

    /**
     * Converte DTO para Entity.
     * O campo 'active' é ignorado (gerenciado pelo sistema).
     */
    @Mapping(target = "active", ignore = true)
    Estados toEntity(EstadosDTO dto);

    /**
     * Converte Entity para DTO.
     */
    EstadosDTO toDTO(Estados entity);

    /**
     * Converte Request para Entity.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    Estados fromRequest(EstadosRequest request);

    /**
     * Atualiza Entity existente com dados do Request.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    void updateFromRequest(EstadosRequest request, @MappingTarget Estados entity);

    /**
     * Converte Entity para Response.
     */
    EstadosResponse toResponse(Estados entity);
}
