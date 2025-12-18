package com.upsaude.mapper.paciente.deficiencia;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.upsaude.api.request.deficiencia.DeficienciasPacienteRequest;
import com.upsaude.api.response.deficiencia.DeficienciasPacienteResponse;
import com.upsaude.entity.paciente.deficiencia.DeficienciasPaciente;
import com.upsaude.mapper.config.MappingConfig;
import com.upsaude.mapper.paciente.PacienteMapper;

@Mapper(config = MappingConfig.class, uses = {com.upsaude.mapper.paciente.deficiencia.DeficienciasMapper.class, PacienteMapper.class})
public interface DeficienciasPacienteMapper {

    @Mapping(target = "active", ignore = true)
    DeficienciasPaciente toEntity(DeficienciasPacienteResponse dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "deficiencia", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    DeficienciasPaciente fromRequest(DeficienciasPacienteRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "deficiencia", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    void updateFromRequest(DeficienciasPacienteRequest request, @MappingTarget DeficienciasPaciente entity);

    @Mapping(target = "paciente", ignore = true)
    DeficienciasPacienteResponse toResponse(DeficienciasPaciente entity);
}
