package com.upsaude.mapper.medicacao;

import com.upsaude.api.request.medicacao.MedicacaoPacienteRequest;
import com.upsaude.api.response.medicacao.MedicacaoPacienteResponse;
import com.upsaude.dto.MedicacaoPacienteDTO;
import com.upsaude.entity.medicacao.MedicacaoPaciente;
import com.upsaude.entity.medicacao.Medicacao;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = {MedicacaoMapper.class, PacienteMapper.class})
public interface MedicacaoPacienteMapper extends EntityMapper<MedicacaoPaciente, MedicacaoPacienteDTO> {

    @Mapping(target = "active", ignore = true)
    MedicacaoPaciente toEntity(MedicacaoPacienteDTO dto);

    MedicacaoPacienteDTO toDTO(MedicacaoPaciente entity);

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
