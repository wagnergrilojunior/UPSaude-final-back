package com.upsaude.mapper;

import com.upsaude.api.request.FabricantesMedicamentoRequest;
import com.upsaude.api.response.FabricantesMedicamentoResponse;
import com.upsaude.dto.FabricantesMedicamentoDTO;
import com.upsaude.entity.FabricantesMedicamento;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para conversões de FabricantesMedicamento.
 * Entity ↔ DTO ↔ Request/Response
 */
@Mapper(config = MappingConfig.class)
public interface FabricantesMedicamentoMapper extends EntityMapper<FabricantesMedicamento, FabricantesMedicamentoDTO> {

    /**
     * Converte DTO para Entity.
     * O campo 'active' é ignorado (gerenciado pelo sistema).
     */
    @Mapping(target = "active", ignore = true)
    FabricantesMedicamento toEntity(FabricantesMedicamentoDTO dto);

    /**
     * Converte Entity para DTO.
     */
    FabricantesMedicamentoDTO toDTO(FabricantesMedicamento entity);

    /**
     * Converte Request para Entity.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    FabricantesMedicamento fromRequest(FabricantesMedicamentoRequest request);

    /**
     * Atualiza Entity existente com dados do Request.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    void updateFromRequest(FabricantesMedicamentoRequest request, @MappingTarget FabricantesMedicamento entity);

    /**
     * Converte Entity para Response.
     */
    FabricantesMedicamentoResponse toResponse(FabricantesMedicamento entity);
}
