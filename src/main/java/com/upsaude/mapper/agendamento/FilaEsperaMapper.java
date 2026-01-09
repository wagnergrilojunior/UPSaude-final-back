package com.upsaude.mapper.agendamento;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import com.upsaude.api.request.agendamento.FilaEsperaRequest;
import com.upsaude.api.response.agendamento.FilaEsperaResponse;
import com.upsaude.api.response.agendamento.EstabelecimentoSimplificadoResponse;
import com.upsaude.api.response.clinica.atendimento.PacienteAtendimentoResponse;
import com.upsaude.api.response.clinica.atendimento.ProfissionalAtendimentoResponse;
import com.upsaude.api.response.clinica.atendimento.MedicoConsultaResponse;
import com.upsaude.entity.agendamento.FilaEspera;
import com.upsaude.entity.estabelecimento.Estabelecimentos;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.entity.profissional.Medicos;
import com.upsaude.entity.profissional.ProfissionaisSaude;
import com.upsaude.mapper.config.MappingConfig;
import com.upsaude.mapper.estabelecimento.EstabelecimentosMapper;
import com.upsaude.mapper.paciente.PacienteMapper;
import com.upsaude.mapper.profissional.MedicosMapper;
import com.upsaude.mapper.profissional.ProfissionaisSaudeMapper;
import com.upsaude.mapper.clinica.atendimento.AtendimentoMapper;
import com.upsaude.mapper.clinica.atendimento.ConsultaMapper;

@Mapper(config = MappingConfig.class, uses = {AgendamentoMapper.class, MedicosMapper.class, PacienteMapper.class, ProfissionaisSaudeMapper.class, EstabelecimentosMapper.class, AtendimentoMapper.class, ConsultaMapper.class})
public interface FilaEsperaMapper {

    @Mapping(target = "active", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    @Mapping(target = "agendamento", ignore = true)
    FilaEspera toEntity(FilaEsperaResponse dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "estabelecimento", ignore = true)
    @Mapping(target = "agendamento", ignore = true)
    @Mapping(target = "medico", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    FilaEspera fromRequest(FilaEsperaRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "estabelecimento", ignore = true)
    @Mapping(target = "agendamento", ignore = true)
    @Mapping(target = "medico", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    void updateFromRequest(FilaEsperaRequest request, @MappingTarget FilaEspera entity);

    @Mapping(target = "estabelecimento", source = "estabelecimento", qualifiedByName = "mapEstabelecimentoSimplificado")
    @Mapping(target = "paciente", source = "paciente", qualifiedByName = "mapPacienteSimplificadoFilaEspera")
    @Mapping(target = "profissional", source = "profissional", qualifiedByName = "mapProfissionalSimplificadoFilaEspera")
    @Mapping(target = "medico", source = "medico", qualifiedByName = "mapMedicoSimplificadoFilaEspera")
    @Mapping(target = "agendamento", source = "agendamento", qualifiedByName = "mapAgendamentoSimplificado")
    FilaEsperaResponse toResponse(FilaEspera entity);

    @Named("mapEstabelecimentoSimplificado")
    default EstabelecimentoSimplificadoResponse mapEstabelecimentoSimplificado(Estabelecimentos estabelecimento) {
        if (estabelecimento == null) {
            return null;
        }
        String razaoSocial = estabelecimento.getDadosIdentificacao() != null 
            ? estabelecimento.getDadosIdentificacao().getNome()
            : null;
        String nomeFantasia = estabelecimento.getDadosIdentificacao() != null 
            ? estabelecimento.getDadosIdentificacao().getNomeFantasia()
            : null;
        return EstabelecimentoSimplificadoResponse.builder()
            .id(estabelecimento.getId())
            .razaoSocial(razaoSocial)
            .nomeFantasia(nomeFantasia)
            .build();
    }

    @Named("mapPacienteSimplificadoFilaEspera")
    default PacienteAtendimentoResponse mapPacienteSimplificadoFilaEspera(Paciente paciente) {
        if (paciente == null) {
            return null;
        }
        com.upsaude.mapper.clinica.atendimento.AtendimentoMapper mapper = org.mapstruct.factory.Mappers.getMapper(com.upsaude.mapper.clinica.atendimento.AtendimentoMapper.class);
        return mapper.mapPacienteSimplificado(paciente);
    }

    @Named("mapProfissionalSimplificadoFilaEspera")
    default ProfissionalAtendimentoResponse mapProfissionalSimplificadoFilaEspera(ProfissionaisSaude profissional) {
        if (profissional == null) {
            return null;
        }
        com.upsaude.mapper.clinica.atendimento.AtendimentoMapper mapper = org.mapstruct.factory.Mappers.getMapper(com.upsaude.mapper.clinica.atendimento.AtendimentoMapper.class);
        return mapper.mapProfissionalSimplificado(profissional);
    }

    @Named("mapMedicoSimplificadoFilaEspera")
    default MedicoConsultaResponse mapMedicoSimplificadoFilaEspera(Medicos medico) {
        if (medico == null) {
            return null;
        }
        com.upsaude.mapper.clinica.atendimento.ConsultaMapper mapper = org.mapstruct.factory.Mappers.getMapper(com.upsaude.mapper.clinica.atendimento.ConsultaMapper.class);
        return mapper.mapMedicoSimplificado(medico);
    }

    @Named("mapAgendamentoSimplificado")
    default com.upsaude.api.response.agendamento.AgendamentoResponse mapAgendamentoSimplificado(com.upsaude.entity.agendamento.Agendamento agendamento) {
        if (agendamento == null) {
            return null;
        }
        com.upsaude.mapper.agendamento.AgendamentoMapper mapper = org.mapstruct.factory.Mappers.getMapper(com.upsaude.mapper.agendamento.AgendamentoMapper.class);
        return mapper.toResponse(agendamento);
    }
}
