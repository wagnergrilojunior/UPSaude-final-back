package com.upsaude.mapper;

import com.upsaude.api.request.DeficienciasPacienteRequest;
import com.upsaude.api.response.DeficienciasPacienteResponse;
import com.upsaude.dto.DeficienciasPacienteDTO;
import com.upsaude.entity.DeficienciasPaciente;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper para conversão entre entidades, DTOs, Requests e Responses de Deficiências de Paciente.
 *
 * @author UPSaúde
 */
@Mapper(config = MappingConfig.class)
public interface DeficienciasPacienteMapper extends EntityMapper<DeficienciasPaciente, DeficienciasPacienteDTO> {

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "deficiencia", ignore = true)
    DeficienciasPaciente toEntity(DeficienciasPacienteDTO dto);

    DeficienciasPacienteDTO toDTO(DeficienciasPaciente entity);

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "deficiencia", ignore = true)
    DeficienciasPaciente fromRequest(DeficienciasPacienteRequest request);

    @Mapping(target = "pacienteId", source = "paciente.id")
    @Mapping(target = "deficienciaId", source = "deficiencia.id")
    @Mapping(target = "deficienciaNome", source = "deficiencia.nome")
    DeficienciasPacienteResponse toResponse(DeficienciasPaciente entity);
}

