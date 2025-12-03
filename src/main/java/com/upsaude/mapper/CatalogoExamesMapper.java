package com.upsaude.mapper;

import com.upsaude.api.request.CatalogoExamesRequest;
import com.upsaude.api.response.CatalogoExamesResponse;
import com.upsaude.dto.CatalogoExamesDTO;
import com.upsaude.entity.CatalogoExames;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para conversões de CatalogoExames.
 * Entity ↔ DTO ↔ Request/Response
 */
@Mapper(config = MappingConfig.class)
public interface CatalogoExamesMapper extends EntityMapper<CatalogoExames, CatalogoExamesDTO> {

    /**
     * Converte DTO para Entity.
     * O campo 'active' é ignorado (gerenciado pelo sistema).
     */
    @Mapping(target = "active", ignore = true)
    CatalogoExames toEntity(CatalogoExamesDTO dto);

    /**
     * Converte Entity para DTO.
     */
    CatalogoExamesDTO toDTO(CatalogoExames entity);

    /**
     * Converte Request para Entity.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    CatalogoExames fromRequest(CatalogoExamesRequest request);

    /**
     * Atualiza Entity existente com dados do Request.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    void updateFromRequest(CatalogoExamesRequest request, @MappingTarget CatalogoExames entity);

    /**
     * Converte Entity para Response.
     */
    CatalogoExamesResponse toResponse(CatalogoExames entity);
}
