package com.upsaude.mapper;

import com.upsaude.api.request.FabricantesEquipamentoRequest;
import com.upsaude.api.response.FabricantesEquipamentoResponse;
import com.upsaude.dto.FabricantesEquipamentoDTO;
import com.upsaude.entity.FabricantesEquipamento;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para conversões de FabricantesEquipamento.
 * Entity ↔ DTO ↔ Request/Response
 */
@Mapper(config = MappingConfig.class)
public interface FabricantesEquipamentoMapper extends EntityMapper<FabricantesEquipamento, FabricantesEquipamentoDTO> {

    /**
     * Converte DTO para Entity.
     * O campo 'active' é ignorado (gerenciado pelo sistema).
     */
    @Mapping(target = "active", ignore = true)
    FabricantesEquipamento toEntity(FabricantesEquipamentoDTO dto);

    /**
     * Converte Entity para DTO.
     */
    FabricantesEquipamentoDTO toDTO(FabricantesEquipamento entity);

    /**
     * Converte Request para Entity.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    FabricantesEquipamento fromRequest(FabricantesEquipamentoRequest request);

    /**
     * Atualiza Entity existente com dados do Request.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    void updateFromRequest(FabricantesEquipamentoRequest request, @MappingTarget FabricantesEquipamento entity);

    /**
     * Converte Entity para Response.
     */
    FabricantesEquipamentoResponse toResponse(FabricantesEquipamento entity);
}
