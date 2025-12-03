package com.upsaude.mapper;

import com.upsaude.api.request.EquipeSaudeRequest;
import com.upsaude.api.response.EquipeSaudeResponse;
import com.upsaude.dto.EquipeSaudeDTO;
import com.upsaude.entity.EquipeSaude;
import com.upsaude.entity.Estabelecimentos;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para conversões de EquipeSaude.
 * Entity ↔ DTO ↔ Request/Response
 */
@Mapper(config = MappingConfig.class, uses = {EstabelecimentosMapper.class})
public interface EquipeSaudeMapper extends EntityMapper<EquipeSaude, EquipeSaudeDTO> {

    /**
     * Converte DTO para Entity.
     * O campo 'active' é ignorado (gerenciado pelo sistema).
     */
    @Mapping(target = "active", ignore = true)
    EquipeSaude toEntity(EquipeSaudeDTO dto);

    /**
     * Converte Entity para DTO.
     */
    EquipeSaudeDTO toDTO(EquipeSaude entity);

    /**
     * Converte Request para Entity.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "estabelecimento", ignore = true)
    EquipeSaude fromRequest(EquipeSaudeRequest request);

    /**
     * Atualiza Entity existente com dados do Request.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "estabelecimento", ignore = true)
    void updateFromRequest(EquipeSaudeRequest request, @MappingTarget EquipeSaude entity);

    /**
     * Converte Entity para Response.
     */
    EquipeSaudeResponse toResponse(EquipeSaude entity);
}
