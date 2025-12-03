package com.upsaude.mapper;

import com.upsaude.api.request.ProcedimentosOdontologicosRequest;
import com.upsaude.api.response.ProcedimentosOdontologicosResponse;
import com.upsaude.dto.ProcedimentosOdontologicosDTO;
import com.upsaude.entity.ProcedimentosOdontologicos;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para conversões de ProcedimentosOdontologicos.
 * Entity ↔ DTO ↔ Request/Response
 */
@Mapper(config = MappingConfig.class)
public interface ProcedimentosOdontologicosMapper extends EntityMapper<ProcedimentosOdontologicos, ProcedimentosOdontologicosDTO> {

    /**
     * Converte DTO para Entity.
     * O campo 'active' é ignorado (gerenciado pelo sistema).
     */
    @Mapping(target = "active", ignore = true)
    ProcedimentosOdontologicos toEntity(ProcedimentosOdontologicosDTO dto);

    /**
     * Converte Entity para DTO.
     */
    ProcedimentosOdontologicosDTO toDTO(ProcedimentosOdontologicos entity);

    /**
     * Converte Request para Entity.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    ProcedimentosOdontologicos fromRequest(ProcedimentosOdontologicosRequest request);

    /**
     * Atualiza Entity existente com dados do Request.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    void updateFromRequest(ProcedimentosOdontologicosRequest request, @MappingTarget ProcedimentosOdontologicos entity);

    /**
     * Converte Entity para Response.
     */
    ProcedimentosOdontologicosResponse toResponse(ProcedimentosOdontologicos entity);
}
