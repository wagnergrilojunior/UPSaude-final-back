package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.DiagnosticoDoencaPacienteRequest;
import com.upsaude.api.response.embeddable.DiagnosticoDoencaPacienteResponse;
import com.upsaude.entity.embeddable.DiagnosticoDoencaPaciente;
import com.upsaude.dto.embeddable.DiagnosticoDoencaPacienteDTO;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface DiagnosticoDoencaPacienteMapper {
    DiagnosticoDoencaPaciente toEntity(DiagnosticoDoencaPacienteRequest request);
    DiagnosticoDoencaPacienteResponse toResponse(DiagnosticoDoencaPaciente entity);
    void updateFromRequest(DiagnosticoDoencaPacienteRequest request, @MappingTarget DiagnosticoDoencaPaciente entity);

    // Mapeamento entre DTO e Entity
    DiagnosticoDoencaPaciente toEntity(com.upsaude.dto.embeddable.DiagnosticoDoencaPacienteDTO dto);
    com.upsaude.dto.embeddable.DiagnosticoDoencaPacienteDTO toDTO(DiagnosticoDoencaPaciente entity);
    void updateFromDTO(com.upsaude.dto.embeddable.DiagnosticoDoencaPacienteDTO dto, @MappingTarget DiagnosticoDoencaPaciente entity);
}
