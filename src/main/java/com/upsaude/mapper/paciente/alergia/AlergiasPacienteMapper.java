package com.upsaude.mapper.paciente.alergia;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.upsaude.api.request.alergia.AlergiasPacienteRequest;
import com.upsaude.api.response.alergia.AlergiasPacienteResponse;
import com.upsaude.entity.paciente.alergia.AlergiasPaciente;
import com.upsaude.mapper.config.MappingConfig;
import com.upsaude.mapper.paciente.PacienteMapper;

@Mapper(config = MappingConfig.class, uses = {com.upsaude.mapper.paciente.alergia.AlergiasMapper.class, PacienteMapper.class, com.upsaude.mapper.embeddable.DiagnosticoAlergiaPacienteMapper.class, com.upsaude.mapper.embeddable.HistoricoReacoesAlergiaPacienteMapper.class})
public interface AlergiasPacienteMapper {

    @Mapping(target = "active", ignore = true)
    AlergiasPaciente toEntity(AlergiasPacienteResponse dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "alergia", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    AlergiasPaciente fromRequest(AlergiasPacienteRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "alergia", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    void updateFromRequest(AlergiasPacienteRequest request, @MappingTarget AlergiasPaciente entity);

    @Mapping(target = "paciente", ignore = true)
    AlergiasPacienteResponse toResponse(AlergiasPaciente entity);
}
