package com.upsaude.mapper;

import com.upsaude.api.request.MedicacaoPacienteRequest;
import com.upsaude.api.response.MedicacaoPacienteResponse;
import com.upsaude.dto.MedicacaoPacienteDTO;
import com.upsaude.entity.MedicacaoPaciente;
import com.upsaude.entity.CidDoencas;
import com.upsaude.entity.Medicacao;
import com.upsaude.entity.Paciente;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = {CidDoencasMapper.class, MedicacaoMapper.class, PacienteMapper.class})
public interface MedicacaoPacienteMapper extends EntityMapper<MedicacaoPaciente, MedicacaoPacienteDTO> {

    @Mapping(target = "active", ignore = true)
    MedicacaoPaciente toEntity(MedicacaoPacienteDTO dto);

    MedicacaoPacienteDTO toDTO(MedicacaoPaciente entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "cidRelacionado", ignore = true)
    @Mapping(target = "medicacao", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    MedicacaoPaciente fromRequest(MedicacaoPacienteRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "cidRelacionado", ignore = true)
    @Mapping(target = "medicacao", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    void updateFromRequest(MedicacaoPacienteRequest request, @MappingTarget MedicacaoPaciente entity);

    @Mapping(target = "paciente", ignore = true)
    MedicacaoPacienteResponse toResponse(MedicacaoPaciente entity);
}
