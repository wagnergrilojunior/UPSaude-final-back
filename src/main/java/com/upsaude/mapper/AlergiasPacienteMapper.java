package com.upsaude.mapper;

import com.upsaude.api.request.AlergiasPacienteRequest;
import com.upsaude.api.response.AlergiasPacienteResponse;
import com.upsaude.dto.AlergiasPacienteDTO;
import com.upsaude.entity.AlergiasPaciente;
import com.upsaude.entity.Alergias;
import com.upsaude.entity.Paciente;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para conversões de AlergiasPaciente.
 * Entity ↔ DTO ↔ Request/Response
 */
@Mapper(config = MappingConfig.class, uses = {AlergiasMapper.class, PacienteMapper.class})
public interface AlergiasPacienteMapper extends EntityMapper<AlergiasPaciente, AlergiasPacienteDTO> {

    /**
     * Converte DTO para Entity.
     * O campo 'active' é ignorado (gerenciado pelo sistema).
     */
    @Mapping(target = "active", ignore = true)
    AlergiasPaciente toEntity(AlergiasPacienteDTO dto);

    /**
     * Converte Entity para DTO.
     */
    AlergiasPacienteDTO toDTO(AlergiasPaciente entity);

    /**
     * Converte Request para Entity.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "alergia", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    AlergiasPaciente fromRequest(AlergiasPacienteRequest request);

    /**
     * Atualiza Entity existente com dados do Request.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "alergia", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    void updateFromRequest(AlergiasPacienteRequest request, @MappingTarget AlergiasPaciente entity);

    /**
     * Converte Entity para Response.
     */
    AlergiasPacienteResponse toResponse(AlergiasPaciente entity);
}
