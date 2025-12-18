package com.upsaude.mapper.agendamento;
import com.upsaude.api.response.paciente.ResponsavelLegalResponse;
import com.upsaude.api.response.paciente.PacienteResponse;

import com.upsaude.api.request.agendamento.AgendamentoRequest;
import com.upsaude.api.response.agendamento.AgendamentoResponse;
import com.upsaude.dto.AgendamentoDTO;
import com.upsaude.entity.agendamento.Agendamento;
import com.upsaude.mapper.config.MappingConfig;
import com.upsaude.mapper.EntityMapper;
import com.upsaude.mapper.atendimento.AtendimentoMapper;
import com.upsaude.mapper.convenio.ConvenioMapper;
import com.upsaude.mapper.profissional.EspecialidadesMedicasMapper;
import com.upsaude.mapper.profissional.MedicosMapper;
import com.upsaude.mapper.paciente.PacienteMapper;
import com.upsaude.mapper.profissional.ProfissionaisSaudeMapper;
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
