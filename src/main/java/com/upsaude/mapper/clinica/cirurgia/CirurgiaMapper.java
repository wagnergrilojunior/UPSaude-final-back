package com.upsaude.mapper.clinica.cirurgia;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import com.upsaude.api.request.clinica.cirurgia.CirurgiaRequest;
import com.upsaude.api.response.clinica.cirurgia.CirurgiaResponse;
import com.upsaude.api.response.clinica.cirurgia.CirurgiaProcedimentoResponse;
import com.upsaude.api.response.clinica.atendimento.PacienteAtendimentoResponse;
import com.upsaude.api.response.clinica.atendimento.ProfissionalAtendimentoResponse;
import com.upsaude.api.response.clinica.atendimento.MedicoConsultaResponse;
import com.upsaude.api.response.agendamento.ConvenioAgendamentoResponse;
import com.upsaude.api.response.referencia.cid.DiagnosticoPrincipalResponse;
import com.upsaude.entity.clinica.cirurgia.Cirurgia;
import com.upsaude.entity.clinica.cirurgia.CirurgiaProcedimento;
import com.upsaude.entity.clinica.cirurgia.EquipeCirurgica;
import com.upsaude.api.response.clinica.cirurgia.EquipeCirurgicaResponse;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.entity.profissional.Medicos;
import com.upsaude.entity.profissional.ProfissionaisSaude;
import com.upsaude.entity.convenio.Convenio;
import com.upsaude.entity.referencia.cid.Cid10Subcategorias;
import com.upsaude.mapper.config.MappingConfig;
import com.upsaude.mapper.convenio.ConvenioMapper;
import com.upsaude.mapper.paciente.PacienteMapper;
import com.upsaude.mapper.profissional.MedicosMapper;
import com.upsaude.mapper.profissional.ProfissionaisSaudeMapper;
import com.upsaude.mapper.clinica.atendimento.AtendimentoMapper;
import com.upsaude.mapper.clinica.atendimento.ConsultaMapper;
import com.upsaude.mapper.agendamento.AgendamentoMapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

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
    @Mapping(target = "diagnosticoPrincipal", ignore = true)
    @Mapping(target = "procedimentos", ignore = true)
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
    @Mapping(target = "diagnosticoPrincipal", ignore = true)
    @Mapping(target = "procedimentos", ignore = true)
    void updateFromRequest(CirurgiaRequest request, @MappingTarget Cirurgia entity);

    @Mapping(target = "tenantId", source = "tenant.id")
    @Mapping(target = "estabelecimentoId", source = "estabelecimento.id")
    @Mapping(target = "paciente", source = "paciente", qualifiedByName = "mapPacienteSimplificadoCirurgia")
    @Mapping(target = "cirurgiaoPrincipal", source = "cirurgiaoPrincipal", qualifiedByName = "mapProfissionalSimplificadoCirurgia")
    @Mapping(target = "medicoCirurgiao", source = "medicoCirurgiao", qualifiedByName = "mapMedicoSimplificadoCirurgia")
    @Mapping(target = "convenio", source = "convenio", qualifiedByName = "mapConvenioSimplificadoCirurgia")
    @Mapping(target = "diagnosticoPrincipal", source = "diagnosticoPrincipal", qualifiedByName = "mapDiagnosticoPrincipalSimplificado")
    @Mapping(target = "equipe", source = "equipe", qualifiedByName = "mapEquipeCirurgia")
    @Mapping(target = "procedimentos", source = "procedimentos", qualifiedByName = "mapProcedimentosCirurgia")
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

    @Named("mapDiagnosticoPrincipalSimplificado")
    default DiagnosticoPrincipalResponse mapDiagnosticoPrincipalSimplificado(Cid10Subcategorias diagnostico) {
        if (diagnostico == null) {
            return null;
        }
        return DiagnosticoPrincipalResponse.builder()
                .id(diagnostico.getId())
                .subcat(diagnostico.getSubcat())
                .descricao(diagnostico.getDescricao())
                .descrAbrev(diagnostico.getDescrAbrev())
                .build();
    }

    @Named("mapEquipeCirurgia")
    default List<EquipeCirurgicaResponse> mapEquipeCirurgia(List<EquipeCirurgica> equipe) {
        if (equipe == null || equipe.isEmpty()) {
            return new java.util.ArrayList<>();
        }
        EquipeCirurgicaMapper mapper = Mappers.getMapper(EquipeCirurgicaMapper.class);
        return equipe.stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Named("mapProcedimentosCirurgia")
    default List<CirurgiaProcedimentoResponse> mapProcedimentosCirurgia(List<CirurgiaProcedimento> procedimentos) {
        if (procedimentos == null || procedimentos.isEmpty()) {
            return new java.util.ArrayList<>();
        }
        CirurgiaProcedimentoMapper mapper = Mappers.getMapper(CirurgiaProcedimentoMapper.class);
        return procedimentos.stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }
}
