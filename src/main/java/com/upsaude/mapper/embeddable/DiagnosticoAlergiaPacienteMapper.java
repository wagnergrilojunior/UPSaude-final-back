package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.DiagnosticoAlergiaPacienteRequest;
import com.upsaude.api.response.embeddable.DiagnosticoAlergiaPacienteResponse;
import com.upsaude.entity.embeddable.DiagnosticoAlergiaPaciente;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface DiagnosticoAlergiaPacienteMapper {
    DiagnosticoAlergiaPaciente toEntity(DiagnosticoAlergiaPacienteRequest request);
    DiagnosticoAlergiaPacienteResponse toResponse(DiagnosticoAlergiaPaciente entity);
    void updateFromRequest(DiagnosticoAlergiaPacienteRequest request, @MappingTarget DiagnosticoAlergiaPaciente entity);

}
