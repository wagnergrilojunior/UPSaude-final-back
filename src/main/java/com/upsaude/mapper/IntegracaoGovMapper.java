package com.upsaude.mapper;

import com.upsaude.api.request.IntegracaoGovRequest;
import com.upsaude.api.response.IntegracaoGovResponse;
import com.upsaude.dto.IntegracaoGovDTO;
import com.upsaude.entity.IntegracaoGov;
import com.upsaude.entity.Paciente;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para conversões de IntegracaoGov.
 * Entity ↔ DTO ↔ Request/Response
 */
@Mapper(config = MappingConfig.class, uses = {PacienteMapper.class})
public interface IntegracaoGovMapper extends EntityMapper<IntegracaoGov, IntegracaoGovDTO> {

    /**
     * Converte DTO para Entity.
     * O campo 'active' é ignorado (gerenciado pelo sistema).
     */
    @Mapping(target = "active", ignore = true)
    IntegracaoGov toEntity(IntegracaoGovDTO dto);

    /**
     * Converte Entity para DTO.
     */
    IntegracaoGovDTO toDTO(IntegracaoGov entity);

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
    IntegracaoGov fromRequest(IntegracaoGovRequest request);

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
    void updateFromRequest(IntegracaoGovRequest request, @MappingTarget IntegracaoGov entity);

    /**
     * Converte Entity para Response.
     */
    IntegracaoGovResponse toResponse(IntegracaoGov entity);
}
