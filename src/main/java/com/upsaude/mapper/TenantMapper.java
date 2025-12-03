package com.upsaude.mapper;

import com.upsaude.api.request.TenantRequest;
import com.upsaude.api.response.TenantResponse;
import com.upsaude.dto.TenantDTO;
import com.upsaude.entity.Tenant;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para conversões de Tenant.
 * Entity ↔ DTO ↔ Request/Response
 */
@Mapper(config = MappingConfig.class)
public interface TenantMapper extends EntityMapper<Tenant, TenantDTO> {

    /**
     * Converte DTO para Entity.
     * O campo 'active' é ignorado (gerenciado pelo sistema).
     */
    @Mapping(target = "active", ignore = true)
    Tenant toEntity(TenantDTO dto);

    /**
     * Converte Entity para DTO.
     */
    TenantDTO toDTO(Tenant entity);

    /**
     * Converte Request para Entity.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    Tenant fromRequest(TenantRequest request);

    /**
     * Atualiza Entity existente com dados do Request.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    void updateFromRequest(TenantRequest request, @MappingTarget Tenant entity);

    /**
     * Converte Entity para Response.
     */
    TenantResponse toResponse(Tenant entity);
}
