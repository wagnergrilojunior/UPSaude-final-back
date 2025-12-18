package com.upsaude.mapper.clinica.medicacao;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.upsaude.api.request.clinica.medicacao.MedicacaoPacienteRequest;
import com.upsaude.api.response.clinica.medicacao.MedicacaoPacienteResponse;
import com.upsaude.entity.clinica.medicacao.MedicacaoPaciente;
import com.upsaude.mapper.config.MappingConfig;
import com.upsaude.mapper.paciente.PacienteMapper;

@Mapper(config = MappingConfig.class, uses = {MedicacaoMapper.class, PacienteMapper.class})
public interface MedicacaoPacienteMapper {

    @Mapping(target = "active", ignore = true)
    MedicacaoPaciente toEntity(MedicacaoPacienteResponse dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "medicacao", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    MedicacaoPaciente fromRequest(MedicacaoPacienteRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "medicacao", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    void updateFromRequest(MedicacaoPacienteRequest request, @MappingTarget MedicacaoPaciente entity);

    @Mapping(target = "paciente", ignore = true)
    MedicacaoPacienteResponse toResponse(MedicacaoPaciente entity);
}
