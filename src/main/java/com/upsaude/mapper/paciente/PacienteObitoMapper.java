package com.upsaude.mapper.paciente;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.upsaude.api.request.paciente.PacienteObitoRequest;
import com.upsaude.api.response.paciente.PacienteObitoResponse;
import com.upsaude.entity.paciente.PacienteObito;
import com.upsaude.mapper.config.MappingConfig;

@Mapper(config = MappingConfig.class)
public interface PacienteObitoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    PacienteObito fromRequest(PacienteObitoRequest request);

    PacienteObitoResponse toResponse(PacienteObito entity);
}
