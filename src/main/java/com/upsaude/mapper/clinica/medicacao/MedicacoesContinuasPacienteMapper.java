package com.upsaude.mapper.clinica.medicacao;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.upsaude.api.request.clinica.medicacao.MedicacoesContinuasPacienteRequest;
import com.upsaude.api.response.clinica.medicacao.MedicacoesContinuasPacienteResponse;
import com.upsaude.dto.clinica.medicacao.MedicacoesContinuasPacienteDTO;
import com.upsaude.entity.clinica.medicacao.MedicacoesContinuasPaciente;
import com.upsaude.mapper.EntityMapper;
import com.upsaude.mapper.config.MappingConfig;
import com.upsaude.mapper.paciente.PacienteMapper;

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
