package com.upsaude.mapper;

import com.upsaude.api.request.MedicacoesContinuasRequest;
import com.upsaude.api.response.MedicacoesContinuasResponse;
import com.upsaude.dto.MedicacoesContinuasDTO;
import com.upsaude.entity.MedicacoesContinuas;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para conversões de MedicacoesContinuas.
 * Entity ↔ DTO ↔ Request/Response
 */
@Mapper(config = MappingConfig.class)
public interface MedicacoesContinuasMapper extends EntityMapper<MedicacoesContinuas, MedicacoesContinuasDTO> {

    /**
     * Converte DTO para Entity.
     * O campo 'active' é ignorado (gerenciado pelo sistema).
     */
    @Mapping(target = "active", ignore = true)
    MedicacoesContinuas toEntity(MedicacoesContinuasDTO dto);

    /**
     * Converte Entity para DTO.
     */
    MedicacoesContinuasDTO toDTO(MedicacoesContinuas entity);

    /**
     * Converte Request para Entity.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    MedicacoesContinuas fromRequest(MedicacoesContinuasRequest request);

    /**
     * Atualiza Entity existente com dados do Request.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    void updateFromRequest(MedicacoesContinuasRequest request, @MappingTarget MedicacoesContinuas entity);

    /**
     * Converte Entity para Response.
     */
    MedicacoesContinuasResponse toResponse(MedicacoesContinuas entity);
}
