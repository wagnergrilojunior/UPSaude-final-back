package com.upsaude.mapper.clinica.atendimento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.upsaude.api.request.clinica.atendimento.CheckInAtendimentoRequest;
import com.upsaude.api.response.clinica.atendimento.CheckInAtendimentoResponse;
import com.upsaude.dto.clinica.atendimento.CheckInAtendimentoDTO;
import com.upsaude.entity.clinica.atendimento.CheckInAtendimento;
import com.upsaude.mapper.EntityMapper;
import com.upsaude.mapper.agendamento.AgendamentoMapper;
import com.upsaude.mapper.config.MappingConfig;
import com.upsaude.mapper.paciente.PacienteMapper;

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

    @Mapping(target = "agendamento", ignore = true)
    @Mapping(target = "atendimento", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    CheckInAtendimentoResponse toResponse(CheckInAtendimento entity);
}
