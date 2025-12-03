package com.upsaude.mapper;

import com.upsaude.api.request.PermissoesRequest;
import com.upsaude.api.response.PermissoesResponse;
import com.upsaude.dto.PermissoesDTO;
import com.upsaude.entity.Permissoes;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para conversões de Permissoes.
 * Entity ↔ DTO ↔ Request/Response
 */
@Mapper(config = MappingConfig.class)
public interface PermissoesMapper extends EntityMapper<Permissoes, PermissoesDTO> {

    /**
     * Converte DTO para Entity.
     * O campo 'active' é ignorado (gerenciado pelo sistema).
     */
    @Mapping(target = "active", ignore = true)
    Permissoes toEntity(PermissoesDTO dto);

    /**
     * Converte Entity para DTO.
     */
    PermissoesDTO toDTO(Permissoes entity);

    /**
     * Converte Request para Entity.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    Permissoes fromRequest(PermissoesRequest request);

    /**
     * Atualiza Entity existente com dados do Request.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    void updateFromRequest(PermissoesRequest request, @MappingTarget Permissoes entity);

    /**
     * Converte Entity para Response.
     */
    PermissoesResponse toResponse(Permissoes entity);
}
