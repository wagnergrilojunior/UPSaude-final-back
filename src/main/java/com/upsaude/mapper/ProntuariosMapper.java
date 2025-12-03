package com.upsaude.mapper;

import com.upsaude.api.request.ProntuariosRequest;
import com.upsaude.api.response.ProntuariosResponse;
import com.upsaude.dto.ProntuariosDTO;
import com.upsaude.entity.Prontuarios;
import com.upsaude.entity.Paciente;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para conversões de Prontuarios.
 * Entity ↔ DTO ↔ Request/Response
 */
@Mapper(config = MappingConfig.class, uses = {PacienteMapper.class})
public interface ProntuariosMapper extends EntityMapper<Prontuarios, ProntuariosDTO> {

    /**
     * Converte DTO para Entity.
     * O campo 'active' é ignorado (gerenciado pelo sistema).
     */
    @Mapping(target = "active", ignore = true)
    Prontuarios toEntity(ProntuariosDTO dto);

    /**
     * Converte Entity para DTO.
     */
    ProntuariosDTO toDTO(Prontuarios entity);

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
    Prontuarios fromRequest(ProntuariosRequest request);

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
    void updateFromRequest(ProntuariosRequest request, @MappingTarget Prontuarios entity);

    /**
     * Converte Entity para Response.
     */
    ProntuariosResponse toResponse(Prontuarios entity);
}
