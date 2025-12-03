package com.upsaude.mapper;

import com.upsaude.api.request.ConsultaPuericulturaRequest;
import com.upsaude.api.response.ConsultaPuericulturaResponse;
import com.upsaude.dto.ConsultaPuericulturaDTO;
import com.upsaude.entity.ConsultaPuericultura;
import com.upsaude.entity.ProfissionaisSaude;
import com.upsaude.entity.Puericultura;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para conversões de ConsultaPuericultura.
 * Entity ↔ DTO ↔ Request/Response
 */
@Mapper(config = MappingConfig.class, uses = {ProfissionaisSaudeMapper.class, PuericulturaMapper.class})
public interface ConsultaPuericulturaMapper extends EntityMapper<ConsultaPuericultura, ConsultaPuericulturaDTO> {

    /**
     * Converte DTO para Entity.
     * O campo 'active' é ignorado (gerenciado pelo sistema).
     */
    @Mapping(target = "active", ignore = true)
    ConsultaPuericultura toEntity(ConsultaPuericulturaDTO dto);

    /**
     * Converte Entity para DTO.
     */
    ConsultaPuericulturaDTO toDTO(ConsultaPuericultura entity);

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
    @Mapping(target = "puericultura", ignore = true)
    ConsultaPuericultura fromRequest(ConsultaPuericulturaRequest request);

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
    @Mapping(target = "puericultura", ignore = true)
    void updateFromRequest(ConsultaPuericulturaRequest request, @MappingTarget ConsultaPuericultura entity);

    /**
     * Converte Entity para Response.
     */
    ConsultaPuericulturaResponse toResponse(ConsultaPuericultura entity);
}
