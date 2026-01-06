package com.upsaude.mapper.paciente;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.upsaude.api.request.paciente.PacienteIdentificadorRequest;
import com.upsaude.api.response.paciente.PacienteIdentificadorResponse;
import com.upsaude.entity.paciente.PacienteIdentificador;
import com.upsaude.mapper.config.MappingConfig;

@Mapper(config = MappingConfig.class)
public interface PacienteIdentificadorMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "validado", ignore = true)
    @Mapping(target = "dataValidacao", ignore = true)
    PacienteIdentificador fromRequest(PacienteIdentificadorRequest request);

    PacienteIdentificadorResponse toResponse(PacienteIdentificador entity);
}
