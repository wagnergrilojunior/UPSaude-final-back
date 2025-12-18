package com.upsaude.mapper.deficiencia;

import com.upsaude.api.request.deficiencia.DeficienciasPacienteRequest;
import com.upsaude.api.response.deficiencia.DeficienciasPacienteResponse;
import com.upsaude.dto.DeficienciasPacienteDTO;
import com.upsaude.entity.deficiencia.DeficienciasPaciente;
import com.upsaude.entity.deficiencia.Deficiencias;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = {DeficienciasMapper.class, PacienteMapper.class})
public interface DeficienciasPacienteMapper extends EntityMapper<DeficienciasPaciente, DeficienciasPacienteDTO> {

    @Mapping(target = "active", ignore = true)
    DeficienciasPaciente toEntity(DeficienciasPacienteDTO dto);

    DeficienciasPacienteDTO toDTO(DeficienciasPaciente entity);

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
