package com.upsaude.mapper;

import com.upsaude.api.request.DoencasCronicasPacienteRequest;
import com.upsaude.api.response.DoencasCronicasPacienteResponse;
import com.upsaude.dto.DoencasCronicasPacienteDTO;
import com.upsaude.entity.DoencasCronicasPaciente;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MappingConfig.class)
public interface DoencasCronicasPacienteMapper extends EntityMapper<DoencasCronicasPaciente, DoencasCronicasPacienteDTO> {

    @Mapping(target = "tenant", ignore = true)
    DoencasCronicasPaciente toEntity(DoencasCronicasPacienteDTO dto);

    DoencasCronicasPacienteDTO toDTO(DoencasCronicasPaciente entity);

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "active", ignore = true)
    DoencasCronicasPaciente fromRequest(DoencasCronicasPacienteRequest request);

    DoencasCronicasPacienteResponse toResponse(DoencasCronicasPaciente entity);
}

