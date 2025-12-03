package com.upsaude.mapper;

import com.upsaude.api.request.ProfissionaisSaudeRequest;
import com.upsaude.api.response.ProfissionaisSaudeResponse;
import com.upsaude.dto.ProfissionaisSaudeDTO;
import com.upsaude.entity.ProfissionaisSaude;
import com.upsaude.entity.ConselhosProfissionais;
import com.upsaude.entity.Endereco;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para conversões de ProfissionaisSaude.
 * Entity ↔ DTO ↔ Request/Response
 */
@Mapper(config = MappingConfig.class, uses = {ConselhosProfissionaisMapper.class, EnderecoMapper.class})
public interface ProfissionaisSaudeMapper extends EntityMapper<ProfissionaisSaude, ProfissionaisSaudeDTO> {

    /**
     * Converte DTO para Entity.
     * O campo 'active' é ignorado (gerenciado pelo sistema).
     */
    @Mapping(target = "active", ignore = true)
    ProfissionaisSaude toEntity(ProfissionaisSaudeDTO dto);

    /**
     * Converte Entity para DTO.
     */
    ProfissionaisSaudeDTO toDTO(ProfissionaisSaude entity);

    /**
     * Converte Request para Entity.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "conselho", ignore = true)
    @Mapping(target = "enderecoProfissional", ignore = true)
    ProfissionaisSaude fromRequest(ProfissionaisSaudeRequest request);

    /**
     * Atualiza Entity existente com dados do Request.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "conselho", ignore = true)
    @Mapping(target = "enderecoProfissional", ignore = true)
    void updateFromRequest(ProfissionaisSaudeRequest request, @MappingTarget ProfissionaisSaude entity);

    /**
     * Converte Entity para Response.
     */
    ProfissionaisSaudeResponse toResponse(ProfissionaisSaude entity);
}
