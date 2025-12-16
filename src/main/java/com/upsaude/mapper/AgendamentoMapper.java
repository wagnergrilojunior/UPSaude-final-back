package com.upsaude.mapper;

import com.upsaude.api.request.AgendamentoRequest;
import com.upsaude.api.response.AgendamentoResponse;
import com.upsaude.dto.AgendamentoDTO;
import com.upsaude.entity.Agendamento;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = {AtendimentoMapper.class, ConvenioMapper.class, EspecialidadesMedicasMapper.class, MedicosMapper.class, PacienteMapper.class, ProfissionaisSaudeMapper.class})
public interface AgendamentoMapper extends EntityMapper<Agendamento, AgendamentoDTO> {

    @Mapping(target = "active", ignore = true)
    Agendamento toEntity(AgendamentoDTO dto);

    AgendamentoDTO toDTO(Agendamento entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "agendamentoOriginal", ignore = true)
    @Mapping(target = "atendimento", ignore = true)
    @Mapping(target = "convenio", ignore = true)
    @Mapping(target = "especialidade", ignore = true)
    @Mapping(target = "medico", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    @Mapping(target = "reagendamentos", ignore = true)
    @Mapping(target = "notificacoes", ignore = true)
    @Mapping(target = "checkIns", ignore = true)
    Agendamento fromRequest(AgendamentoRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "agendamentoOriginal", ignore = true)
    @Mapping(target = "atendimento", ignore = true)
    @Mapping(target = "convenio", ignore = true)
    @Mapping(target = "especialidade", ignore = true)
    @Mapping(target = "medico", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    @Mapping(target = "reagendamentos", ignore = true)
    @Mapping(target = "notificacoes", ignore = true)
    @Mapping(target = "checkIns", ignore = true)
    void updateFromRequest(AgendamentoRequest request, @MappingTarget Agendamento entity);

    // Evita recursão infinita na serialização:
    // AgendamentoResponse -> agendamentoOriginal/reagendamentos -> AgendamentoResponse ...
    @Mapping(target = "agendamentoOriginal", ignore = true)
    @Mapping(target = "reagendamentos", ignore = true)
    @Mapping(target = "notificacoes", ignore = true)
    @Mapping(target = "checkIns", ignore = true)
    // Evita ciclos/recursões indiretas via PacienteResponse/ResponsavelLegalResponse e outros grafos grandes.
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    @Mapping(target = "medico", ignore = true)
    @Mapping(target = "especialidade", ignore = true)
    @Mapping(target = "convenio", ignore = true)
    @Mapping(target = "atendimento", ignore = true)
    AgendamentoResponse toResponse(Agendamento entity);
}
