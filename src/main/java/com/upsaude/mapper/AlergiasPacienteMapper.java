package com.upsaude.mapper;

import com.upsaude.api.request.AlergiasPacienteRequest;
import com.upsaude.api.response.AlergiasPacienteResponse;
import com.upsaude.dto.AlergiasPacienteDTO;
import com.upsaude.entity.AlergiasPaciente;
import com.upsaude.entity.Alergias;
import com.upsaude.entity.Paciente;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = {AlergiasMapper.class, PacienteMapper.class, com.upsaude.mapper.embeddable.DiagnosticoAlergiaPacienteMapper.class, com.upsaude.mapper.embeddable.HistoricoReacoesAlergiaPacienteMapper.class})
public interface AlergiasPacienteMapper extends EntityMapper<AlergiasPaciente, AlergiasPacienteDTO> {

    @Mapping(target = "active", ignore = true)
    AlergiasPaciente toEntity(AlergiasPacienteDTO dto);

    AlergiasPacienteDTO toDTO(AlergiasPaciente entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "alergia", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    AlergiasPaciente fromRequest(AlergiasPacienteRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "alergia", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    void updateFromRequest(AlergiasPacienteRequest request, @MappingTarget AlergiasPaciente entity);

    @Mapping(target = "paciente", ignore = true)
    AlergiasPacienteResponse toResponse(AlergiasPaciente entity);
}
