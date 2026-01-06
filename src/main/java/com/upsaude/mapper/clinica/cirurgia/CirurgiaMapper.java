package com.upsaude.mapper.clinica.cirurgia;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import com.upsaude.api.request.clinica.cirurgia.CirurgiaRequest;
import com.upsaude.api.response.clinica.cirurgia.CirurgiaResponse;
import com.upsaude.api.response.clinica.atendimento.PacienteAtendimentoResponse;
import com.upsaude.api.response.clinica.atendimento.ProfissionalAtendimentoResponse;
import com.upsaude.api.response.clinica.atendimento.MedicoConsultaResponse;
import com.upsaude.api.response.agendamento.ConvenioAgendamentoResponse;
import com.upsaude.entity.clinica.cirurgia.Cirurgia;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.entity.profissional.Medicos;
import com.upsaude.entity.profissional.ProfissionaisSaude;
import com.upsaude.entity.convenio.Convenio;
import com.upsaude.mapper.config.MappingConfig;
import com.upsaude.mapper.convenio.ConvenioMapper;
import com.upsaude.mapper.paciente.PacienteMapper;
import com.upsaude.mapper.profissional.MedicosMapper;
import com.upsaude.mapper.profissional.ProfissionaisSaudeMapper;
import com.upsaude.mapper.clinica.atendimento.AtendimentoMapper;
import com.upsaude.mapper.clinica.atendimento.ConsultaMapper;
import com.upsaude.mapper.agendamento.AgendamentoMapper;

@Mapper(config = MappingConfig.class, uses = {ConvenioMapper.class, MedicosMapper.class, PacienteMapper.class, ProfissionaisSaudeMapper.class, AtendimentoMapper.class, ConsultaMapper.class, AgendamentoMapper.class})
public interface CirurgiaMapper  {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "cirurgiaoPrincipal", ignore = true)
    @Mapping(target = "convenio", ignore = true)
    @Mapping(target = "medicoCirurgiao", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "equipe", ignore = true)
    Cirurgia fromRequest(CirurgiaRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "cirurgiaoPrincipal", ignore = true)
    @Mapping(target = "convenio", ignore = true)
    @Mapping(target = "medicoCirurgiao", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "equipe", ignore = true)
    void updateFromRequest(CirurgiaRequest request, @MappingTarget Cirurgia entity);

    @Mapping(target = "paciente", source = "paciente", qualifiedByName = "mapPacienteSimplificadoCirurgia")
    @Mapping(target = "cirurgiaoPrincipal", source = "cirurgiaoPrincipal", qualifiedByName = "mapProfissionalSimplificadoCirurgia")
    @Mapping(target = "medicoCirurgiao", source = "medicoCirurgiao", qualifiedByName = "mapMedicoSimplificadoCirurgia")
    @Mapping(target = "convenio", source = "convenio", qualifiedByName = "mapConvenioSimplificadoCirurgia")
    @Mapping(target = "equipe", ignore = true)
    CirurgiaResponse toResponse(Cirurgia entity);

    @Named("mapPacienteSimplificadoCirurgia")
    default PacienteAtendimentoResponse mapPacienteSimplificadoCirurgia(Paciente paciente) {
        if (paciente == null) {
            return null;
        }
        com.upsaude.mapper.clinica.atendimento.AtendimentoMapper mapper = org.mapstruct.factory.Mappers.getMapper(com.upsaude.mapper.clinica.atendimento.AtendimentoMapper.class);
        return mapper.mapPacienteSimplificado(paciente);
    }

    @Named("mapProfissionalSimplificadoCirurgia")
    default ProfissionalAtendimentoResponse mapProfissionalSimplificadoCirurgia(ProfissionaisSaude profissional) {
        if (profissional == null) {
            return null;
        }
        com.upsaude.mapper.clinica.atendimento.AtendimentoMapper mapper = org.mapstruct.factory.Mappers.getMapper(com.upsaude.mapper.clinica.atendimento.AtendimentoMapper.class);
        return mapper.mapProfissionalSimplificado(profissional);
    }

    @Named("mapMedicoSimplificadoCirurgia")
    default MedicoConsultaResponse mapMedicoSimplificadoCirurgia(Medicos medico) {
        if (medico == null) {
            return null;
        }
        com.upsaude.mapper.clinica.atendimento.ConsultaMapper mapper = org.mapstruct.factory.Mappers.getMapper(com.upsaude.mapper.clinica.atendimento.ConsultaMapper.class);
        return mapper.mapMedicoSimplificado(medico);
    }

    @Named("mapConvenioSimplificadoCirurgia")
    default ConvenioAgendamentoResponse mapConvenioSimplificadoCirurgia(Convenio convenio) {
        if (convenio == null) {
            return null;
        }
        com.upsaude.mapper.agendamento.AgendamentoMapper mapper = org.mapstruct.factory.Mappers.getMapper(com.upsaude.mapper.agendamento.AgendamentoMapper.class);
        return mapper.mapConvenioSimplificado(convenio);
    }
}
