package com.upsaude.mapper;

import com.upsaude.api.request.UsuariosPerfisRequest;
import com.upsaude.api.response.UsuariosPerfisResponse;
import com.upsaude.dto.UsuariosPerfisDTO;
import com.upsaude.entity.UsuariosPerfis;
import com.upsaude.entity.Papeis;
import com.upsaude.entity.UsuariosSistema;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para conversões de UsuariosPerfis.
 * Entity ↔ DTO ↔ Request/Response
 */
@Mapper(config = MappingConfig.class, uses = {PapeisMapper.class, UsuariosSistemaMapper.class})
public interface UsuariosPerfisMapper extends EntityMapper<UsuariosPerfis, UsuariosPerfisDTO> {

    /**
     * Converte DTO para Entity.
     * O campo 'active' é ignorado (gerenciado pelo sistema).
     */
    @Mapping(target = "active", ignore = true)
    UsuariosPerfis toEntity(UsuariosPerfisDTO dto);

    /**
     * Converte Entity para DTO.
     */
    UsuariosPerfisDTO toDTO(UsuariosPerfis entity);

    /**
     * Converte Request para Entity.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "papel", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    UsuariosPerfis fromRequest(UsuariosPerfisRequest request);

    /**
     * Atualiza Entity existente com dados do Request.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "papel", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    void updateFromRequest(UsuariosPerfisRequest request, @MappingTarget UsuariosPerfis entity);

    /**
     * Converte Entity para Response.
     */
    UsuariosPerfisResponse toResponse(UsuariosPerfis entity);
}
