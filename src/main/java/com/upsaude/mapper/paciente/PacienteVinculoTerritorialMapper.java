package com.upsaude.mapper.paciente;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.upsaude.api.request.paciente.PacienteVinculoTerritorialRequest;
import com.upsaude.api.response.paciente.PacienteVinculoTerritorialResponse;
import com.upsaude.entity.paciente.PacienteVinculoTerritorial;
import com.upsaude.mapper.config.MappingConfig;

@Mapper(config = MappingConfig.class)
public interface PacienteVinculoTerritorialMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "estabelecimento", ignore = true)
    PacienteVinculoTerritorial fromRequest(PacienteVinculoTerritorialRequest request);

    PacienteVinculoTerritorialResponse toResponse(PacienteVinculoTerritorial entity);
}
