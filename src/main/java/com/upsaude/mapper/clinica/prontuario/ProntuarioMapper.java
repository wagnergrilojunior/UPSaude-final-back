package com.upsaude.mapper.clinica.prontuario;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import com.upsaude.api.request.clinica.prontuario.ProntuarioRequest;
import com.upsaude.api.response.clinica.prontuario.ProntuarioResponse;
import com.upsaude.api.response.clinica.atendimento.PacienteAtendimentoResponse;
import com.upsaude.api.response.clinica.atendimento.ProfissionalAtendimentoResponse;
import com.upsaude.entity.clinica.prontuario.Prontuario;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.entity.profissional.ProfissionaisSaude;
import com.upsaude.mapper.config.MappingConfig;
import com.upsaude.mapper.paciente.PacienteMapper;
import com.upsaude.mapper.profissional.ProfissionaisSaudeMapper;
import com.upsaude.mapper.clinica.prontuario.AlergiaPacienteMapper;
import com.upsaude.mapper.clinica.prontuario.VacinacaoPacienteMapper;
import com.upsaude.mapper.clinica.prontuario.ExamePacienteMapper;
import com.upsaude.mapper.clinica.prontuario.DoencaPacienteMapper;
import com.upsaude.mapper.clinica.atendimento.AtendimentoMapper;

@Mapper(config = MappingConfig.class, uses = {PacienteMapper.class, ProfissionaisSaudeMapper.class, AlergiaPacienteMapper.class, VacinacaoPacienteMapper.class, ExamePacienteMapper.class, DoencaPacienteMapper.class, AtendimentoMapper.class})
public interface ProntuarioMapper  {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "profissionalCriador", ignore = true)
    Prontuario fromRequest(ProntuarioRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "profissionalCriador", ignore = true)
    void updateFromRequest(ProntuarioRequest request, @MappingTarget Prontuario entity);

    @Mapping(target = "paciente", source = "paciente", qualifiedByName = "mapPacienteSimplificadoProntuario")
    @Mapping(target = "profissionalCriador", source = "profissionalCriador", qualifiedByName = "mapProfissionalSimplificadoProntuario")
    @Mapping(target = "alergias", source = "alergias")
    @Mapping(target = "vacinacoes", source = "vacinacoes")
    @Mapping(target = "exames", source = "exames")
    @Mapping(target = "doencas", source = "doencas")
    ProntuarioResponse toResponse(Prontuario entity);

    @Named("mapPacienteSimplificadoProntuario")
    default PacienteAtendimentoResponse mapPacienteSimplificadoProntuario(Paciente paciente) {
        if (paciente == null) {
            return null;
        }
        com.upsaude.mapper.clinica.atendimento.AtendimentoMapper mapper = org.mapstruct.factory.Mappers.getMapper(com.upsaude.mapper.clinica.atendimento.AtendimentoMapper.class);
        return mapper.mapPacienteSimplificado(paciente);
    }

    @Named("mapProfissionalSimplificadoProntuario")
    default ProfissionalAtendimentoResponse mapProfissionalSimplificadoProntuario(ProfissionaisSaude profissional) {
        if (profissional == null) {
            return null;
        }
        com.upsaude.mapper.clinica.atendimento.AtendimentoMapper mapper = org.mapstruct.factory.Mappers.getMapper(com.upsaude.mapper.clinica.atendimento.AtendimentoMapper.class);
        return mapper.mapProfissionalSimplificado(profissional);
    }
}

