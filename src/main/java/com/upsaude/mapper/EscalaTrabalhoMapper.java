package com.upsaude.mapper;

import com.upsaude.api.request.EscalaTrabalhoRequest;
import com.upsaude.api.response.EscalaTrabalhoResponse;
import com.upsaude.dto.EscalaTrabalhoDTO;
import com.upsaude.entity.EscalaTrabalho;
import com.upsaude.entity.Medicos;
import com.upsaude.entity.ProfissionaisSaude;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para conversões de EscalaTrabalho.
 * Entity ↔ DTO ↔ Request/Response
 */
@Mapper(config = MappingConfig.class, uses = {MedicosMapper.class, ProfissionaisSaudeMapper.class})
public interface EscalaTrabalhoMapper extends EntityMapper<EscalaTrabalho, EscalaTrabalhoDTO> {

    /**
     * Converte DTO para Entity.
     * O campo 'active' é ignorado (gerenciado pelo sistema).
     */
    @Mapping(target = "active", ignore = true)
    EscalaTrabalho toEntity(EscalaTrabalhoDTO dto);

    /**
     * Converte Entity para DTO.
     */
    EscalaTrabalhoDTO toDTO(EscalaTrabalho entity);

    /**
     * Converte Request para Entity.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "medico", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    EscalaTrabalho fromRequest(EscalaTrabalhoRequest request);

    /**
     * Atualiza Entity existente com dados do Request.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "medico", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    void updateFromRequest(EscalaTrabalhoRequest request, @MappingTarget EscalaTrabalho entity);

    /**
     * Converte Entity para Response.
     */
    EscalaTrabalhoResponse toResponse(EscalaTrabalho entity);
}
