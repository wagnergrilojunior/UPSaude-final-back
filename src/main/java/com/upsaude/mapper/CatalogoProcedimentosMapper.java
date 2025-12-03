package com.upsaude.mapper;

import com.upsaude.api.request.CatalogoProcedimentosRequest;
import com.upsaude.api.response.CatalogoProcedimentosResponse;
import com.upsaude.dto.CatalogoProcedimentosDTO;
import com.upsaude.entity.CatalogoProcedimentos;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para conversões de CatalogoProcedimentos.
 * Entity ↔ DTO ↔ Request/Response
 */
@Mapper(config = MappingConfig.class)
public interface CatalogoProcedimentosMapper extends EntityMapper<CatalogoProcedimentos, CatalogoProcedimentosDTO> {

    /**
     * Converte DTO para Entity.
     * O campo 'active' é ignorado (gerenciado pelo sistema).
     */
    @Mapping(target = "active", ignore = true)
    CatalogoProcedimentos toEntity(CatalogoProcedimentosDTO dto);

    /**
     * Converte Entity para DTO.
     */
    CatalogoProcedimentosDTO toDTO(CatalogoProcedimentos entity);

    /**
     * Converte Request para Entity.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    CatalogoProcedimentos fromRequest(CatalogoProcedimentosRequest request);

    /**
     * Atualiza Entity existente com dados do Request.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    void updateFromRequest(CatalogoProcedimentosRequest request, @MappingTarget CatalogoProcedimentos entity);

    /**
     * Converte Entity para Response.
     */
    CatalogoProcedimentosResponse toResponse(CatalogoProcedimentos entity);
}
