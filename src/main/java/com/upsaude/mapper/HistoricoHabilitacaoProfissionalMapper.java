package com.upsaude.mapper;

import com.upsaude.api.request.HistoricoHabilitacaoProfissionalRequest;
import com.upsaude.api.response.HistoricoHabilitacaoProfissionalResponse;
import com.upsaude.dto.HistoricoHabilitacaoProfissionalDTO;
import com.upsaude.entity.HistoricoHabilitacaoProfissional;
import com.upsaude.entity.ProfissionaisSaude;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para conversões de HistoricoHabilitacaoProfissional.
 * Entity ↔ DTO ↔ Request/Response
 */
@Mapper(config = MappingConfig.class, uses = {ProfissionaisSaudeMapper.class})
public interface HistoricoHabilitacaoProfissionalMapper extends EntityMapper<HistoricoHabilitacaoProfissional, HistoricoHabilitacaoProfissionalDTO> {

    /**
     * Converte DTO para Entity.
     * O campo 'active' é ignorado (gerenciado pelo sistema).
     */
    @Mapping(target = "active", ignore = true)
    HistoricoHabilitacaoProfissional toEntity(HistoricoHabilitacaoProfissionalDTO dto);

    /**
     * Converte Entity para DTO.
     */
    HistoricoHabilitacaoProfissionalDTO toDTO(HistoricoHabilitacaoProfissional entity);

    /**
     * Converte Request para Entity.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    HistoricoHabilitacaoProfissional fromRequest(HistoricoHabilitacaoProfissionalRequest request);

    /**
     * Atualiza Entity existente com dados do Request.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    void updateFromRequest(HistoricoHabilitacaoProfissionalRequest request, @MappingTarget HistoricoHabilitacaoProfissional entity);

    /**
     * Converte Entity para Response.
     */
    HistoricoHabilitacaoProfissionalResponse toResponse(HistoricoHabilitacaoProfissional entity);
}
