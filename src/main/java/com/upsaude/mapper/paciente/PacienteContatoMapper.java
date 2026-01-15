package com.upsaude.mapper.paciente;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.upsaude.api.request.paciente.PacienteContatoRequest;
import com.upsaude.api.response.paciente.PacienteContatoResponse;
import com.upsaude.entity.paciente.PacienteContato;
import com.upsaude.mapper.config.MappingConfig;

@Mapper(config = MappingConfig.class)
public interface PacienteContatoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "estabelecimento", ignore = true)
    PacienteContato fromRequest(PacienteContatoRequest request);

    PacienteContatoResponse toResponse(PacienteContato entity);
}
