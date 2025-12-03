package com.upsaude.mapper;

import com.upsaude.api.request.ResponsavelLegalRequest;
import com.upsaude.api.response.ResponsavelLegalResponse;
import com.upsaude.dto.ResponsavelLegalDTO;
import com.upsaude.entity.ResponsavelLegal;
import com.upsaude.entity.Paciente;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para conversões de ResponsavelLegal.
 * Entity ↔ DTO ↔ Request/Response
 */
@Mapper(config = MappingConfig.class, uses = {PacienteMapper.class})
public interface ResponsavelLegalMapper extends EntityMapper<ResponsavelLegal, ResponsavelLegalDTO> {

    /**
     * Converte DTO para Entity.
     * O campo 'active' é ignorado (gerenciado pelo sistema).
     */
    @Mapping(target = "active", ignore = true)
    ResponsavelLegal toEntity(ResponsavelLegalDTO dto);

    /**
     * Converte Entity para DTO.
     */
    ResponsavelLegalDTO toDTO(ResponsavelLegal entity);

    /**
     * Converte Request para Entity.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    ResponsavelLegal fromRequest(ResponsavelLegalRequest request);

    /**
     * Atualiza Entity existente com dados do Request.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    void updateFromRequest(ResponsavelLegalRequest request, @MappingTarget ResponsavelLegal entity);

    /**
     * Converte Entity para Response.
     */
    ResponsavelLegalResponse toResponse(ResponsavelLegal entity);
}
