package com.upsaude.mapper;

import com.upsaude.api.request.AlergiasPacienteRequest;
import com.upsaude.api.response.AlergiasPacienteResponse;
import com.upsaude.dto.AlergiasPacienteDTO;
import com.upsaude.entity.AlergiasPaciente;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MappingConfig.class)
public interface AlergiasPacienteMapper extends EntityMapper<AlergiasPaciente, AlergiasPacienteDTO> {

    @Mapping(target = "tenant", ignore = true)
    AlergiasPaciente toEntity(AlergiasPacienteDTO dto);

    AlergiasPacienteDTO toDTO(AlergiasPaciente entity);

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "active", ignore = true)
    AlergiasPaciente fromRequest(AlergiasPacienteRequest request);

    AlergiasPacienteResponse toResponse(AlergiasPaciente entity);
}

