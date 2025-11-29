package com.upsaude.mapper;

import com.upsaude.api.request.AgendamentoRequest;
import com.upsaude.api.response.AgendamentoResponse;
import com.upsaude.dto.AgendamentoDTO;
import com.upsaude.entity.Agendamento;
import com.upsaude.entity.Atendimento;
import com.upsaude.entity.Convenio;
import com.upsaude.entity.EspecialidadesMedicas;
import com.upsaude.entity.Medicos;
import com.upsaude.entity.Paciente;
import com.upsaude.entity.ProfissionaisSaude;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

/**
 * Mapper para conversão entre entidades, DTOs, Requests e Responses de Agendamento.
 *
 * @author UPSaúde
 */
@Mapper(config = MappingConfig.class)
public interface AgendamentoMapper extends EntityMapper<Agendamento, AgendamentoDTO> {

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "paciente", source = "pacienteId", qualifiedByName = "pacienteFromId")
    @Mapping(target = "profissional", source = "profissionalId", qualifiedByName = "profissionalFromId")
    @Mapping(target = "medico", source = "medicoId", qualifiedByName = "medicoFromId")
    @Mapping(target = "especialidade", source = "especialidadeId", qualifiedByName = "especialidadeFromId")
    @Mapping(target = "convenio", source = "convenioId", qualifiedByName = "convenioFromId")
    @Mapping(target = "atendimento", source = "atendimentoId", qualifiedByName = "atendimentoFromId")
    @Mapping(target = "agendamentoOriginal", source = "agendamentoOriginalId", qualifiedByName = "agendamentoFromId")
    @Mapping(target = "reagendamentos", ignore = true)
    @Mapping(target = "notificacoes", ignore = true)
    @Mapping(target = "checkIns", ignore = true)
    Agendamento toEntity(AgendamentoDTO dto);

    @Mapping(target = "pacienteId", source = "paciente.id")
    @Mapping(target = "profissionalId", source = "profissional.id")
    @Mapping(target = "medicoId", source = "medico.id")
    @Mapping(target = "especialidadeId", source = "especialidade.id")
    @Mapping(target = "convenioId", source = "convenio.id")
    @Mapping(target = "atendimentoId", source = "atendimento.id")
    @Mapping(target = "agendamentoOriginalId", source = "agendamentoOriginal.id")
    AgendamentoDTO toDTO(Agendamento entity);

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "paciente", source = "pacienteId", qualifiedByName = "pacienteFromId")
    @Mapping(target = "profissional", source = "profissionalId", qualifiedByName = "profissionalFromId")
    @Mapping(target = "medico", source = "medicoId", qualifiedByName = "medicoFromId")
    @Mapping(target = "especialidade", source = "especialidadeId", qualifiedByName = "especialidadeFromId")
    @Mapping(target = "convenio", source = "convenioId", qualifiedByName = "convenioFromId")
    @Mapping(target = "atendimento", ignore = true)
    @Mapping(target = "agendamentoOriginal", source = "agendamentoOriginalId", qualifiedByName = "agendamentoFromId")
    @Mapping(target = "reagendamentos", ignore = true)
    @Mapping(target = "notificacoes", ignore = true)
    @Mapping(target = "checkIns", ignore = true)
    @Mapping(target = "dataCancelamento", ignore = true)
    @Mapping(target = "canceladoPor", ignore = true)
    @Mapping(target = "dataReagendamento", ignore = true)
    @Mapping(target = "reagendadoPor", ignore = true)
    @Mapping(target = "agendadoPor", ignore = true)
    @Mapping(target = "confirmadoPor", ignore = true)
    @Mapping(target = "dataConfirmacao", ignore = true)
    @Mapping(target = "dataUltimaAlteracao", ignore = true)
    @Mapping(target = "alteradoPor", ignore = true)
    Agendamento fromRequest(AgendamentoRequest request);

    @Mapping(target = "pacienteId", source = "paciente.id")
    @Mapping(target = "pacienteNome", source = "paciente.nomeCompleto")
    @Mapping(target = "profissionalId", source = "profissional.id")
    @Mapping(target = "profissionalNome", source = "profissional.nomeCompleto")
    @Mapping(target = "medicoId", source = "medico.id")
    @Mapping(target = "medicoNome", source = "medico.nomeCompleto")
    @Mapping(target = "especialidadeId", source = "especialidade.id")
    @Mapping(target = "especialidadeNome", source = "especialidade.nome")
    @Mapping(target = "convenioId", source = "convenio.id")
    @Mapping(target = "convenioNome", source = "convenio.nome")
    @Mapping(target = "atendimentoId", source = "atendimento.id")
    @Mapping(target = "agendamentoOriginalId", source = "agendamentoOriginal.id")
    @Mapping(target = "statusDescricao", source = "status.descricao")
    @Mapping(target = "prioridadeDescricao", source = "prioridade.descricao")
    AgendamentoResponse toResponse(Agendamento entity);

    @Named("pacienteFromId")
    default Paciente pacienteFromId(UUID id) {
        if (id == null) return null;
        Paciente p = new Paciente();
        p.setId(id);
        return p;
    }

    @Named("profissionalFromId")
    default ProfissionaisSaude profissionalFromId(UUID id) {
        if (id == null) return null;
        ProfissionaisSaude p = new ProfissionaisSaude();
        p.setId(id);
        return p;
    }

    @Named("medicoFromId")
    default Medicos medicoFromId(UUID id) {
        if (id == null) return null;
        Medicos m = new Medicos();
        m.setId(id);
        return m;
    }

    @Named("especialidadeFromId")
    default EspecialidadesMedicas especialidadeFromId(UUID id) {
        if (id == null) return null;
        EspecialidadesMedicas e = new EspecialidadesMedicas();
        e.setId(id);
        return e;
    }

    @Named("convenioFromId")
    default Convenio convenioFromId(UUID id) {
        if (id == null) return null;
        Convenio c = new Convenio();
        c.setId(id);
        return c;
    }

    @Named("atendimentoFromId")
    default Atendimento atendimentoFromId(UUID id) {
        if (id == null) return null;
        Atendimento a = new Atendimento();
        a.setId(id);
        return a;
    }

    @Named("agendamentoFromId")
    default Agendamento agendamentoFromId(UUID id) {
        if (id == null) return null;
        Agendamento a = new Agendamento();
        a.setId(id);
        return a;
    }
}

