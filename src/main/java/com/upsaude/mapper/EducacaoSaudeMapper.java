package com.upsaude.mapper;

import com.upsaude.api.request.EducacaoSaudeRequest;
import com.upsaude.api.response.EducacaoSaudeResponse;
import com.upsaude.dto.EducacaoSaudeDTO;
import com.upsaude.entity.EducacaoSaude;
import com.upsaude.entity.EquipeSaude;
import com.upsaude.entity.ProfissionaisSaude;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para conversões de EducacaoSaude.
 * Entity ↔ DTO ↔ Request/Response
 */
@Mapper(config = MappingConfig.class, uses = {EquipeSaudeMapper.class, ProfissionaisSaudeMapper.class})
public interface EducacaoSaudeMapper extends EntityMapper<EducacaoSaude, EducacaoSaudeDTO> {

    /**
     * Converte DTO para Entity.
     * O campo 'active' é ignorado (gerenciado pelo sistema).
     */
    @Mapping(target = "active", ignore = true)
    EducacaoSaude toEntity(EducacaoSaudeDTO dto);

    /**
     * Converte Entity para DTO.
     */
    EducacaoSaudeDTO toDTO(EducacaoSaude entity);

    /**
     * Converte Request para Entity.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "equipeSaude", ignore = true)
    @Mapping(target = "profissionalResponsavel", ignore = true)
    @Mapping(target = "participantes", ignore = true)
    @Mapping(target = "profissionaisParticipantes", ignore = true)
    EducacaoSaude fromRequest(EducacaoSaudeRequest request);

    /**
     * Atualiza Entity existente com dados do Request.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "equipeSaude", ignore = true)
    @Mapping(target = "profissionalResponsavel", ignore = true)
    @Mapping(target = "participantes", ignore = true)
    @Mapping(target = "profissionaisParticipantes", ignore = true)
    void updateFromRequest(EducacaoSaudeRequest request, @MappingTarget EducacaoSaude entity);

    /**
     * Converte Entity para Response.
     */
    EducacaoSaudeResponse toResponse(EducacaoSaude entity);
}
