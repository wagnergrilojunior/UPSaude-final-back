package com.upsaude.mapper;

import com.upsaude.api.request.ConselhosProfissionaisRequest;
import com.upsaude.api.response.ConselhosProfissionaisResponse;
import com.upsaude.dto.ConselhosProfissionaisDTO;
import com.upsaude.entity.ConselhosProfissionais;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para conversões de ConselhosProfissionais.
 * Entity ↔ DTO ↔ Request/Response
 */
@Mapper(config = MappingConfig.class)
public interface ConselhosProfissionaisMapper extends EntityMapper<ConselhosProfissionais, ConselhosProfissionaisDTO> {

    /**
     * Converte DTO para Entity.
     * O campo 'active' é ignorado (gerenciado pelo sistema).
     */
    @Mapping(target = "active", ignore = true)
    ConselhosProfissionais toEntity(ConselhosProfissionaisDTO dto);

    /**
     * Converte Entity para DTO.
     */
    ConselhosProfissionaisDTO toDTO(ConselhosProfissionais entity);

    /**
     * Converte Request para Entity.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    ConselhosProfissionais fromRequest(ConselhosProfissionaisRequest request);

    /**
     * Atualiza Entity existente com dados do Request.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    void updateFromRequest(ConselhosProfissionaisRequest request, @MappingTarget ConselhosProfissionais entity);

    /**
     * Converte Entity para Response.
     */
    ConselhosProfissionaisResponse toResponse(ConselhosProfissionais entity);
}
