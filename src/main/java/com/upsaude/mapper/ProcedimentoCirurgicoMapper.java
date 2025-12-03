package com.upsaude.mapper;

import com.upsaude.api.request.ProcedimentoCirurgicoRequest;
import com.upsaude.api.response.ProcedimentoCirurgicoResponse;
import com.upsaude.dto.ProcedimentoCirurgicoDTO;
import com.upsaude.entity.ProcedimentoCirurgico;
import com.upsaude.entity.Cirurgia;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para conversões de ProcedimentoCirurgico.
 * Entity ↔ DTO ↔ Request/Response
 */
@Mapper(config = MappingConfig.class, uses = {CirurgiaMapper.class})
public interface ProcedimentoCirurgicoMapper extends EntityMapper<ProcedimentoCirurgico, ProcedimentoCirurgicoDTO> {

    /**
     * Converte DTO para Entity.
     * O campo 'active' é ignorado (gerenciado pelo sistema).
     */
    @Mapping(target = "active", ignore = true)
    ProcedimentoCirurgico toEntity(ProcedimentoCirurgicoDTO dto);

    /**
     * Converte Entity para DTO.
     */
    ProcedimentoCirurgicoDTO toDTO(ProcedimentoCirurgico entity);

    /**
     * Converte Request para Entity.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "cirurgia", ignore = true)
    ProcedimentoCirurgico fromRequest(ProcedimentoCirurgicoRequest request);

    /**
     * Atualiza Entity existente com dados do Request.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "cirurgia", ignore = true)
    void updateFromRequest(ProcedimentoCirurgicoRequest request, @MappingTarget ProcedimentoCirurgico entity);

    /**
     * Converte Entity para Response.
     */
    ProcedimentoCirurgicoResponse toResponse(ProcedimentoCirurgico entity);
}
