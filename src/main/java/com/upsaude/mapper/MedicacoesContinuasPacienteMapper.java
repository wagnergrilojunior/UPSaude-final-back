package com.upsaude.mapper;

import com.upsaude.api.request.MedicacoesContinuasPacienteRequest;
import com.upsaude.api.response.MedicacoesContinuasPacienteResponse;
import com.upsaude.dto.MedicacoesContinuasPacienteDTO;
import com.upsaude.entity.MedicacoesContinuasPaciente;
import com.upsaude.entity.MedicacoesContinuas;
import com.upsaude.entity.Paciente;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = {MedicacoesContinuasMapper.class, PacienteMapper.class})
public interface MedicacoesContinuasPacienteMapper extends EntityMapper<MedicacoesContinuasPaciente, MedicacoesContinuasPacienteDTO> {

    @Mapping(target = "active", ignore = true)
    MedicacoesContinuasPaciente toEntity(MedicacoesContinuasPacienteDTO dto);

    MedicacoesContinuasPacienteDTO toDTO(MedicacoesContinuasPaciente entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "medicacaoContinua", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    MedicacoesContinuasPaciente fromRequest(MedicacoesContinuasPacienteRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "medicacaoContinua", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    void updateFromRequest(MedicacoesContinuasPacienteRequest request, @MappingTarget MedicacoesContinuasPaciente entity);

    MedicacoesContinuasPacienteResponse toResponse(MedicacoesContinuasPaciente entity);
}
