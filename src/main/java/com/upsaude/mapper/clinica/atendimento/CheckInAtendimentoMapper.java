package com.upsaude.mapper.clinica.atendimento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import com.upsaude.api.request.clinica.atendimento.CheckInAtendimentoRequest;
import com.upsaude.api.response.clinica.atendimento.CheckInAtendimentoResponse;
import com.upsaude.api.response.clinica.atendimento.PacienteAtendimentoResponse;
import com.upsaude.entity.clinica.atendimento.CheckInAtendimento;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.mapper.agendamento.AgendamentoMapper;
import com.upsaude.mapper.config.MappingConfig;
import com.upsaude.mapper.paciente.PacienteMapper;

@Mapper(config = MappingConfig.class, uses = {AgendamentoMapper.class, AtendimentoMapper.class, PacienteMapper.class})
public interface CheckInAtendimentoMapper {

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
    @Mapping(target = "atendimento", source = "atendimento", qualifiedByName = "mapAtendimentoCheckIn")
    @Mapping(target = "paciente", source = "paciente", qualifiedByName = "mapPacienteSimplificadoCheckIn")
    CheckInAtendimentoResponse toResponse(CheckInAtendimento entity);

    @Named("mapPacienteSimplificadoCheckIn")
    default PacienteAtendimentoResponse mapPacienteSimplificadoCheckIn(Paciente paciente) {
        if (paciente == null) {
            return null;
        }
        com.upsaude.mapper.clinica.atendimento.AtendimentoMapper mapper = org.mapstruct.factory.Mappers.getMapper(com.upsaude.mapper.clinica.atendimento.AtendimentoMapper.class);
        return mapper.mapPacienteSimplificado(paciente);
    }

    @org.mapstruct.Named("mapAtendimentoCheckIn")
    default com.upsaude.api.response.clinica.atendimento.AtendimentoResponse mapAtendimentoCheckIn(com.upsaude.entity.clinica.atendimento.Atendimento atendimento) {
        if (atendimento == null) {
            return null;
        }
        com.upsaude.mapper.clinica.atendimento.AtendimentoMapper mapper = org.mapstruct.factory.Mappers.getMapper(com.upsaude.mapper.clinica.atendimento.AtendimentoMapper.class);
        return mapper.toResponse(atendimento);
    }
}
