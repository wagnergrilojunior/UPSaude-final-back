package com.upsaude.mapper.paciente;

import com.upsaude.api.request.AlergiaPacienteRequest;
import com.upsaude.api.response.AlergiaPacienteResponse;
import com.upsaude.entity.paciente.AlergiaPaciente;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface AlergiaPacienteMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "estabelecimento", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "dataRegistro", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    AlergiaPaciente toEntity(AlergiaPacienteRequest request);

    @Mapping(target = "pacienteId", source = "paciente.id")
    @Mapping(target = "ativo", source = "active")
    AlergiaPacienteResponse toResponse(AlergiaPaciente entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "estabelecimento", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "dataRegistro", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateFromRequest(AlergiaPacienteRequest request, @MappingTarget AlergiaPaciente entity);
}

