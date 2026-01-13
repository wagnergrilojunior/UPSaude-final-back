package com.upsaude.mapper.agendamento;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import com.upsaude.api.request.agendamento.AgendamentoRequest;
import com.upsaude.api.response.agendamento.AgendamentoResponse;
import com.upsaude.api.response.agendamento.ConvenioAgendamentoResponse;
import com.upsaude.api.response.clinica.atendimento.PacienteAtendimentoResponse;
import com.upsaude.api.response.clinica.atendimento.ProfissionalAtendimentoResponse;
import com.upsaude.api.response.clinica.atendimento.MedicoConsultaResponse;
import com.upsaude.entity.agendamento.Agendamento;
import com.upsaude.entity.convenio.Convenio;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.entity.profissional.Medicos;
import com.upsaude.entity.profissional.ProfissionaisSaude;
import com.upsaude.mapper.clinica.atendimento.AtendimentoMapper;
import com.upsaude.mapper.clinica.atendimento.ConsultaMapper;
import com.upsaude.mapper.config.MappingConfig;
import com.upsaude.mapper.convenio.ConvenioMapper;
import com.upsaude.mapper.paciente.PacienteMapper;
import com.upsaude.mapper.profissional.MedicosMapper;
import com.upsaude.mapper.profissional.ProfissionaisSaudeMapper;

@Mapper(config = MappingConfig.class, uses = { AtendimentoMapper.class, ConvenioMapper.class, MedicosMapper.class,
        PacienteMapper.class, ProfissionaisSaudeMapper.class, ConsultaMapper.class })
public interface AgendamentoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "agendamentoOriginal", ignore = true)
    @Mapping(target = "atendimento", ignore = true)
    @Mapping(target = "convenio", ignore = true)
    @Mapping(target = "medico", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    @Mapping(target = "reagendamentos", ignore = true)
    @Mapping(target = "notificacoes", ignore = true)
    @Mapping(target = "checkIns", ignore = true)
    @Mapping(target = "especialidade", ignore = true)
    @Mapping(target = "competenciaFinanceira", ignore = true)
    @Mapping(target = "tipoAgendamento", source = "request.tipoAgendamento")
    @Mapping(target = "categoriaServico", source = "request.categoriaServico")
    @Mapping(target = "tipoServico", source = "request.tipoServico")
    @Mapping(target = "motivosAgendamento", source = "request.motivosAgendamento")
    @Mapping(target = "periodoSolicitado", source = "request.periodoSolicitado")
    Agendamento fromRequest(AgendamentoRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "agendamentoOriginal", ignore = true)
    @Mapping(target = "atendimento", ignore = true)
    @Mapping(target = "convenio", ignore = true)
    @Mapping(target = "medico", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    @Mapping(target = "reagendamentos", ignore = true)
    @Mapping(target = "notificacoes", ignore = true)
    @Mapping(target = "checkIns", ignore = true)
    @Mapping(target = "especialidade", ignore = true)
    @Mapping(target = "competenciaFinanceira", ignore = true)
    void updateFromRequest(AgendamentoRequest request, @MappingTarget Agendamento entity);

    @Mapping(target = "agendamentoOriginal", ignore = true)
    @Mapping(target = "reagendamentos", ignore = true)
    @Mapping(target = "notificacoes", ignore = true)
    @Mapping(target = "checkIns", ignore = true)
    @Mapping(target = "paciente", source = "paciente", qualifiedByName = "mapPacienteSimplificadoAgendamento")
    @Mapping(target = "profissional", source = "profissional", qualifiedByName = "mapProfissionalSimplificadoAgendamento")
    @Mapping(target = "medico", source = "medico", qualifiedByName = "mapMedicoSimplificadoAgendamento")
    @Mapping(target = "convenio", source = "convenio", qualifiedByName = "mapConvenioSimplificado")
    @Mapping(target = "atendimento", source = "atendimento", qualifiedByName = "mapAtendimentoAgendamento")
    @Mapping(target = "especialidade", source = "especialidade", qualifiedByName = "mapEspecialidadeAgendamento")
    AgendamentoResponse toResponse(Agendamento entity);

    @Named("mapEspecialidadeAgendamento")
    default com.upsaude.api.response.referencia.sigtap.SigtapCboResponse mapEspecialidadeAgendamento(
            com.upsaude.entity.referencia.sigtap.SigtapOcupacao especialidade) {
        if (especialidade == null) {
            return null;
        }
        try {
            com.upsaude.api.response.referencia.sigtap.SigtapCboResponse response = new com.upsaude.api.response.referencia.sigtap.SigtapCboResponse();
            response.setId(especialidade.getId());
            response.setCodigoOficial(especialidade.getCodigoOficial());
            response.setNome(especialidade.getNome());
            return response;
        } catch (jakarta.persistence.EntityNotFoundException e) {
            // Entidade foi deletada ou não existe mais no banco de dados
            // Retorna null para evitar erro na serialização
            return null;
        }
    }

    @Named("mapPacienteSimplificadoAgendamento")
    default PacienteAtendimentoResponse mapPacienteSimplificadoAgendamento(Paciente paciente) {
        if (paciente == null) {
            return null;
        }
        com.upsaude.mapper.clinica.atendimento.AtendimentoMapper mapper = org.mapstruct.factory.Mappers
                .getMapper(com.upsaude.mapper.clinica.atendimento.AtendimentoMapper.class);
        return mapper.mapPacienteSimplificado(paciente);
    }

    @Named("mapProfissionalSimplificadoAgendamento")
    default ProfissionalAtendimentoResponse mapProfissionalSimplificadoAgendamento(ProfissionaisSaude profissional) {
        if (profissional == null) {
            return null;
        }
        com.upsaude.mapper.clinica.atendimento.AtendimentoMapper mapper = org.mapstruct.factory.Mappers
                .getMapper(com.upsaude.mapper.clinica.atendimento.AtendimentoMapper.class);
        return mapper.mapProfissionalSimplificado(profissional);
    }

    @Named("mapMedicoSimplificadoAgendamento")
    default MedicoConsultaResponse mapMedicoSimplificadoAgendamento(Medicos medico) {
        if (medico == null) {
            return null;
        }
        com.upsaude.mapper.clinica.atendimento.ConsultaMapper mapper = org.mapstruct.factory.Mappers
                .getMapper(com.upsaude.mapper.clinica.atendimento.ConsultaMapper.class);
        return mapper.mapMedicoSimplificado(medico);
    }

    @Named("mapConvenioSimplificado")
    default ConvenioAgendamentoResponse mapConvenioSimplificado(Convenio convenio) {
        if (convenio == null) {
            return null;
        }
        return ConvenioAgendamentoResponse.builder()
                .id(convenio.getId())
                .nome(convenio.getNome())
                .build();
    }

    @org.mapstruct.Named("mapAtendimentoAgendamento")
    default com.upsaude.api.response.clinica.atendimento.AtendimentoResponse mapAtendimentoAgendamento(
            com.upsaude.entity.clinica.atendimento.Atendimento atendimento) {
        if (atendimento == null) {
            return null;
        }
        com.upsaude.mapper.clinica.atendimento.AtendimentoMapper mapper = org.mapstruct.factory.Mappers
                .getMapper(com.upsaude.mapper.clinica.atendimento.AtendimentoMapper.class);
        return mapper.toResponse(atendimento);
    }
}
