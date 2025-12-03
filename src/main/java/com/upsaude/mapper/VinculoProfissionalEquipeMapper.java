package com.upsaude.mapper;

import com.upsaude.api.request.VinculoProfissionalEquipeRequest;
import com.upsaude.api.response.VinculoProfissionalEquipeResponse;
import com.upsaude.dto.VinculoProfissionalEquipeDTO;
import com.upsaude.entity.VinculoProfissionalEquipe;
import com.upsaude.entity.EquipeSaude;
import com.upsaude.entity.ProfissionaisSaude;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para conversões de VinculoProfissionalEquipe.
 * Entity ↔ DTO ↔ Request/Response
 */
@Mapper(config = MappingConfig.class, uses = {EquipeSaudeMapper.class, ProfissionaisSaudeMapper.class})
public interface VinculoProfissionalEquipeMapper extends EntityMapper<VinculoProfissionalEquipe, VinculoProfissionalEquipeDTO> {

    /**
     * Converte DTO para Entity.
     * O campo 'active' é ignorado (gerenciado pelo sistema).
     */
    @Mapping(target = "active", ignore = true)
    VinculoProfissionalEquipe toEntity(VinculoProfissionalEquipeDTO dto);

    /**
     * Converte Entity para DTO.
     */
    VinculoProfissionalEquipeDTO toDTO(VinculoProfissionalEquipe entity);

    /**
     * Converte Request para Entity.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "equipe", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    VinculoProfissionalEquipe fromRequest(VinculoProfissionalEquipeRequest request);

    /**
     * Atualiza Entity existente com dados do Request.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "equipe", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    void updateFromRequest(VinculoProfissionalEquipeRequest request, @MappingTarget VinculoProfissionalEquipe entity);

    /**
     * Converte Entity para Response.
     */
    VinculoProfissionalEquipeResponse toResponse(VinculoProfissionalEquipe entity);
}
