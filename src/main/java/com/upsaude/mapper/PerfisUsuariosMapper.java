package com.upsaude.mapper;

import com.upsaude.api.request.PerfisUsuariosRequest;
import com.upsaude.api.response.PerfisUsuariosResponse;
import com.upsaude.dto.PerfisUsuariosDTO;
import com.upsaude.entity.PerfisUsuarios;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para conversões de PerfisUsuarios.
 * Entity ↔ DTO ↔ Request/Response
 */
@Mapper(config = MappingConfig.class)
public interface PerfisUsuariosMapper extends EntityMapper<PerfisUsuarios, PerfisUsuariosDTO> {

    /**
     * Converte DTO para Entity.
     * O campo 'active' é ignorado (gerenciado pelo sistema).
     */
    @Mapping(target = "active", ignore = true)
    PerfisUsuarios toEntity(PerfisUsuariosDTO dto);

    /**
     * Converte Entity para DTO.
     */
    PerfisUsuariosDTO toDTO(PerfisUsuarios entity);

    /**
     * Converte Request para Entity.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    PerfisUsuarios fromRequest(PerfisUsuariosRequest request);

    /**
     * Atualiza Entity existente com dados do Request.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    void updateFromRequest(PerfisUsuariosRequest request, @MappingTarget PerfisUsuarios entity);

    /**
     * Converte Entity para Response.
     */
    PerfisUsuariosResponse toResponse(PerfisUsuarios entity);
}
