package com.upsaude.mapper.clinica.atendimento;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import com.upsaude.api.request.clinica.atendimento.AtendimentoCreateRequest;
import com.upsaude.api.request.clinica.atendimento.AtendimentoRequest;
import com.upsaude.api.response.clinica.atendimento.AtendimentoResponse;
import com.upsaude.api.response.clinica.atendimento.PacienteAtendimentoResponse;
import com.upsaude.api.response.clinica.atendimento.ProfissionalAtendimentoResponse;
import com.upsaude.entity.clinica.atendimento.Atendimento;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.entity.profissional.ProfissionaisSaude;
import com.upsaude.enums.TipoContatoEnum;
import com.upsaude.enums.TipoIdentificadorEnum;
import com.upsaude.mapper.config.MappingConfig;
import com.upsaude.mapper.convenio.ConvenioMapper;
import com.upsaude.mapper.paciente.PacienteMapper;
import com.upsaude.mapper.profissional.ProfissionaisSaudeMapper;
import com.upsaude.mapper.profissional.equipe.EquipeSaudeMapper;

@Mapper(config = MappingConfig.class, uses = {ConvenioMapper.class, EquipeSaudeMapper.class, PacienteMapper.class, ProfissionaisSaudeMapper.class, com.upsaude.mapper.embeddable.InformacoesAtendimentoMapper.class, com.upsaude.mapper.embeddable.AnamneseAtendimentoMapper.class, com.upsaude.mapper.embeddable.DiagnosticoAtendimentoMapper.class, com.upsaude.mapper.embeddable.ProcedimentosRealizadosAtendimentoMapper.class, com.upsaude.mapper.embeddable.ClassificacaoRiscoAtendimentoMapper.class})
public interface AtendimentoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "convenio", ignore = true)
    @Mapping(target = "equipeSaude", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    @Mapping(target = "informacoes", ignore = true)
    @Mapping(target = "anamnese", ignore = true)
    @Mapping(target = "diagnostico", ignore = true)
    @Mapping(target = "procedimentosRealizados", ignore = true)
    @Mapping(target = "classificacaoRisco", ignore = true)
    @Mapping(target = "anotacoes", ignore = true)
    @Mapping(target = "observacoesInternas", ignore = true)
    Atendimento fromCreateRequest(AtendimentoCreateRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "convenio", ignore = true)
    @Mapping(target = "equipeSaude", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    Atendimento fromRequest(AtendimentoRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "convenio", ignore = true)
    @Mapping(target = "equipeSaude", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    void updateFromRequest(AtendimentoRequest request, @MappingTarget Atendimento entity);

    @Mapping(target = "paciente", source = "paciente", qualifiedByName = "mapPacienteSimplificado")
    @Mapping(target = "profissional", source = "profissional", qualifiedByName = "mapProfissionalSimplificado")
    @Mapping(target = "equipeSaude", ignore = true)
    @Mapping(target = "convenio", ignore = true)
    @Mapping(target = "informacoes", ignore = true)
    @Mapping(target = "anamnese", ignore = true)
    @Mapping(target = "diagnostico", ignore = true)
    @Mapping(target = "procedimentosRealizados", ignore = true)
    @Mapping(target = "classificacaoRisco", ignore = true)
    @Mapping(target = "anotacoes", ignore = true)
    @Mapping(target = "observacoesInternas", ignore = true)
    AtendimentoResponse toResponse(Atendimento entity);

    @Named("mapPacienteSimplificado")
    default PacienteAtendimentoResponse mapPacienteSimplificado(Paciente paciente) {
        if (paciente == null) {
            return null;
        }

        String cpf = paciente.getIdentificadores() != null 
            ? paciente.getIdentificadores().stream()
                .filter(id -> id.getTipo() == TipoIdentificadorEnum.CPF && Boolean.TRUE.equals(id.getPrincipal()))
                .map(id -> id.getValor())
                .findFirst().orElse(null)
            : null;

        String cns = paciente.getIdentificadores() != null 
            ? paciente.getIdentificadores().stream()
                .filter(id -> id.getTipo() == TipoIdentificadorEnum.CNS && Boolean.TRUE.equals(id.getPrincipal()))
                .map(id -> id.getValor())
                .findFirst().orElse(null)
            : null;

        String telefone = paciente.getContatos() != null 
            ? paciente.getContatos().stream()
                .filter(c -> c.getTipo() == TipoContatoEnum.TELEFONE)
                .map(c -> c.getTelefone())
                .filter(t -> t != null && !t.trim().isEmpty())
                .findFirst().orElse(null)
            : null;

        String email = paciente.getContatos() != null 
            ? paciente.getContatos().stream()
                .filter(c -> c.getTipo() == TipoContatoEnum.EMAIL)
                .map(c -> c.getEmail())
                .filter(e -> e != null && !e.trim().isEmpty())
                .findFirst().orElse(null)
            : null;

        return PacienteAtendimentoResponse.builder()
            .id(paciente.getId())
            .nomeCompleto(paciente.getNomeCompleto())
            .cpf(cpf)
            .cns(cns)
            .telefone(telefone)
            .email(email)
            .dataNascimento(paciente.getDataNascimento())
            .build();
    }

    @Named("mapProfissionalSimplificado")
    default ProfissionalAtendimentoResponse mapProfissionalSimplificado(ProfissionaisSaude profissional) {
        if (profissional == null) {
            return null;
        }

        String nomeCompleto = profissional.getDadosPessoaisBasicos() != null 
            ? profissional.getDadosPessoaisBasicos().getNomeCompleto()
            : null;

        String registroProfissional = profissional.getRegistroProfissional() != null 
            ? profissional.getRegistroProfissional().getRegistroProfissional()
            : null;

        String ufRegistro = profissional.getRegistroProfissional() != null 
            ? profissional.getRegistroProfissional().getUfRegistro()
            : null;

        return ProfissionalAtendimentoResponse.builder()
            .id(profissional.getId())
            .nomeCompleto(nomeCompleto)
            .registroProfissional(registroProfissional)
            .ufRegistro(ufRegistro)
            .build();
    }
}
