package com.upsaude.mapper;

import com.upsaude.api.request.DeficienciasPacienteRequest;
import com.upsaude.api.response.DeficienciasPacienteResponse;
import com.upsaude.dto.DeficienciasPacienteDTO;
import com.upsaude.entity.DeficienciasPaciente;
import com.upsaude.entity.Deficiencias;
import com.upsaude.entity.Paciente;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para conversões de DeficienciasPaciente.
 * Entity ↔ DTO ↔ Request/Response
 */
@Mapper(config = MappingConfig.class, uses = {DeficienciasMapper.class, PacienteMapper.class})
public interface DeficienciasPacienteMapper extends EntityMapper<DeficienciasPaciente, DeficienciasPacienteDTO> {

    /**
     * Converte DTO para Entity.
     * O campo 'active' é ignorado (gerenciado pelo sistema).
     */
    @Mapping(target = "active", ignore = true)
    DeficienciasPaciente toEntity(DeficienciasPacienteDTO dto);

    /**
     * Converte Entity para DTO.
     */
    DeficienciasPacienteDTO toDTO(DeficienciasPaciente entity);

    /**
     * Converte Request para Entity.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "deficiencia", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    DeficienciasPaciente fromRequest(DeficienciasPacienteRequest request);

    /**
     * Atualiza Entity existente com dados do Request.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "deficiencia", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    void updateFromRequest(DeficienciasPacienteRequest request, @MappingTarget DeficienciasPaciente entity);

    /**
     * Converte Entity para Response.
     */
    DeficienciasPacienteResponse toResponse(DeficienciasPaciente entity);
}
