package com.upsaude.mapper;

import com.upsaude.api.request.PapeisRequest;
import com.upsaude.api.response.PapeisResponse;
import com.upsaude.dto.PapeisDTO;
import com.upsaude.entity.Papeis;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para conversões de Papeis.
 * Entity ↔ DTO ↔ Request/Response
 */
@Mapper(config = MappingConfig.class)
public interface PapeisMapper extends EntityMapper<Papeis, PapeisDTO> {

    /**
     * Converte DTO para Entity.
     * O campo 'active' é ignorado (gerenciado pelo sistema).
     */
    @Mapping(target = "active", ignore = true)
    Papeis toEntity(PapeisDTO dto);

    /**
     * Converte Entity para DTO.
     */
    PapeisDTO toDTO(Papeis entity);

    /**
     * Converte Request para Entity.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    Papeis fromRequest(PapeisRequest request);

    /**
     * Atualiza Entity existente com dados do Request.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    void updateFromRequest(PapeisRequest request, @MappingTarget Papeis entity);

    /**
     * Converte Entity para Response.
     */
    PapeisResponse toResponse(Papeis entity);
}
