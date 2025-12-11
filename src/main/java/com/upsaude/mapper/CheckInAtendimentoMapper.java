package com.upsaude.mapper;

import com.upsaude.api.request.CheckInAtendimentoRequest;
import com.upsaude.api.response.CheckInAtendimentoResponse;
import com.upsaude.dto.CheckInAtendimentoDTO;
import com.upsaude.entity.CheckInAtendimento;
import com.upsaude.entity.Agendamento;
import com.upsaude.entity.Atendimento;
import com.upsaude.entity.Paciente;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = {AgendamentoMapper.class, AtendimentoMapper.class, PacienteMapper.class})
public interface CheckInAtendimentoMapper extends EntityMapper<CheckInAtendimento, CheckInAtendimentoDTO> {

    @Mapping(target = "active", ignore = true)
    CheckInAtendimento toEntity(CheckInAtendimentoDTO dto);

    CheckInAtendimentoDTO toDTO(CheckInAtendimento entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "agendamento", ignore = true)
    @Mapping(target = "atendimento", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    CheckInAtendimento fromRequest(CheckInAtendimentoRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "agendamento", ignore = true)
    @Mapping(target = "atendimento", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    void updateFromRequest(CheckInAtendimentoRequest request, @MappingTarget CheckInAtendimento entity);

    CheckInAtendimentoResponse toResponse(CheckInAtendimento entity);
}
