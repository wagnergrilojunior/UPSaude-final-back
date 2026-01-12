package com.upsaude.mapper.clinica.atendimento;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import com.upsaude.api.request.clinica.atendimento.AtendimentoCreateRequest;
import com.upsaude.api.request.clinica.atendimento.AtendimentoRequest;
import com.upsaude.api.response.clinica.atendimento.AtendimentoResponse;
import com.upsaude.api.response.clinica.atendimento.PacienteAtendimentoResponse;
import com.upsaude.api.response.clinica.atendimento.ProfissionalAtendimentoResponse;
import com.upsaude.api.response.convenio.ConvenioSimplificadoResponse;
import com.upsaude.api.response.profissional.equipe.EquipeSaudeSimplificadoResponse;
import com.upsaude.entity.clinica.atendimento.Atendimento;
import com.upsaude.entity.convenio.Convenio;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.entity.profissional.ProfissionaisSaude;
import com.upsaude.entity.profissional.equipe.EquipeSaude;
import com.upsaude.enums.TipoContatoEnum;
import com.upsaude.enums.TipoIdentificadorEnum;
import com.upsaude.mapper.config.MappingConfig;
import java.util.UUID;
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
    @Mapping(target = "equipeSaude", source = "equipeSaude", qualifiedByName = "mapEquipeSaudeSimplificado")
    @Mapping(target = "convenio", source = "convenio", qualifiedByName = "mapConvenioSimplificado")
    @Mapping(target = "estabelecimentoId", ignore = true)
    @Mapping(target = "tenantId", source = "tenant.id")
    @Mapping(target = "enderecoId", ignore = true)
    AtendimentoResponse toResponse(Atendimento entity);

    @AfterMapping
    default void mapIdsRelacionamentos(@MappingTarget AtendimentoResponse response, Atendimento entity) {
        if (entity == null) {
            return;
        }

        // Mapeia tenantId (sempre existe pois é obrigatório)
        if (entity.getTenant() != null) {
            response.setTenantId(entity.getTenant().getId());
        }

        // Mapeia estabelecimentoId (pode ser null)
        if (entity.getEstabelecimento() != null) {
            response.setEstabelecimentoId(entity.getEstabelecimento().getId());
            
            // Mapeia enderecoId se existir
            if (entity.getEstabelecimento().getEndereco() != null) {
                response.setEnderecoId(entity.getEstabelecimento().getEndereco().getId());
            }
        }
    }

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

    @Named("mapEquipeSaudeSimplificado")
    default EquipeSaudeSimplificadoResponse mapEquipeSaudeSimplificado(EquipeSaude equipeSaude) {
        if (equipeSaude == null) {
            return null;
        }

        UUID estabelecimentoId = equipeSaude.getEstabelecimento() != null 
            ? equipeSaude.getEstabelecimento().getId() 
            : null;

        return EquipeSaudeSimplificadoResponse.builder()
            .id(equipeSaude.getId())
            .nomeReferencia(equipeSaude.getNomeReferencia())
            .tipoEquipe(equipeSaude.getTipoEquipe())
            .estabelecimentoId(estabelecimentoId)
            .build();
    }

    @Named("mapConvenioSimplificado")
    default ConvenioSimplificadoResponse mapConvenioSimplificado(Convenio convenio) {
        if (convenio == null) {
            return null;
        }

        UUID estabelecimentoId = convenio.getEstabelecimento() != null 
            ? convenio.getEstabelecimento().getId() 
            : convenio.getEstabelecimentoId();

        UUID tenantId = convenio.getTenant() != null 
            ? convenio.getTenant().getId() 
            : convenio.getTenantId();

        return ConvenioSimplificadoResponse.builder()
            .id(convenio.getId())
            .nome(convenio.getNome())
            .estabelecimentoId(estabelecimentoId)
            .tenantId(tenantId)
            .build();
    }
}
