package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.DiagnosticoDoencaPacienteRequest;
import com.upsaude.api.response.embeddable.DiagnosticoDoencaPacienteResponse;
import com.upsaude.entity.embeddable.DiagnosticoDoencaPaciente;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface DiagnosticoDoencaPacienteMapper {
    DiagnosticoDoencaPaciente toEntity(DiagnosticoDoencaPacienteRequest request);
    DiagnosticoDoencaPacienteResponse toResponse(DiagnosticoDoencaPaciente entity);
    void updateFromRequest(DiagnosticoDoencaPacienteRequest request, @MappingTarget DiagnosticoDoencaPaciente entity);

}
