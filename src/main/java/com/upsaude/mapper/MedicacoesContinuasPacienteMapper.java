package com.upsaude.mapper;

import com.upsaude.api.request.MedicacoesContinuasPacienteRequest;
import com.upsaude.api.response.MedicacoesContinuasPacienteResponse;
import com.upsaude.dto.MedicacoesContinuasPacienteDTO;
import com.upsaude.entity.MedicacoesContinuasPaciente;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MappingConfig.class)
public interface MedicacoesContinuasPacienteMapper extends EntityMapper<MedicacoesContinuasPaciente, MedicacoesContinuasPacienteDTO> {

    @Mapping(target = "tenant", ignore = true)
    MedicacoesContinuasPaciente toEntity(MedicacoesContinuasPacienteDTO dto);

    MedicacoesContinuasPacienteDTO toDTO(MedicacoesContinuasPaciente entity);

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "active", ignore = true)
    MedicacoesContinuasPaciente fromRequest(MedicacoesContinuasPacienteRequest request);

    MedicacoesContinuasPacienteResponse toResponse(MedicacoesContinuasPaciente entity);
}

