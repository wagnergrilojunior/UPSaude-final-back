package com.upsaude.mapper;

import com.upsaude.api.request.EducacaoSaudeRequest;
import com.upsaude.api.response.EducacaoSaudeResponse;
import com.upsaude.dto.EducacaoSaudeDTO;
import com.upsaude.entity.EducacaoSaude;
import com.upsaude.entity.Estabelecimentos;
import com.upsaude.entity.EquipeSaude;
import com.upsaude.entity.Paciente;
import com.upsaude.entity.ProfissionaisSaude;
import com.upsaude.enums.TipoEducacaoSaudeEnum;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Mapper para conversão entre EducacaoSaude entity, DTO, Request e Response.
 *
 * @author UPSaúde
 */
@Mapper(config = MappingConfig.class)
public interface EducacaoSaudeMapper extends EntityMapper<EducacaoSaude, EducacaoSaudeDTO> {

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "estabelecimento", source = "estabelecimentoId", qualifiedByName = "estabelecimentoFromId")
    @Mapping(target = "profissionalResponsavel", source = "profissionalResponsavelId", qualifiedByName = "profissionalFromId")
    @Mapping(target = "equipeSaude", source = "equipeSaudeId", qualifiedByName = "equipeFromId")
    @Mapping(target = "tipoAtividade", source = "tipoAtividade", qualifiedByName = "tipoAtividadeFromCodigo")
    @Mapping(target = "participantes", source = "participantesIds", qualifiedByName = "pacientesFromIds")
    @Mapping(target = "profissionaisParticipantes", source = "profissionaisParticipantesIds", qualifiedByName = "profissionaisFromIds")
    EducacaoSaude toEntity(EducacaoSaudeDTO dto);

    @Mapping(target = "estabelecimentoId", source = "estabelecimento.id")
    @Mapping(target = "profissionalResponsavelId", source = "profissionalResponsavel.id")
    @Mapping(target = "equipeSaudeId", source = "equipeSaude.id")
    @Mapping(target = "tipoAtividade", source = "tipoAtividade.codigo")
    @Mapping(target = "participantesIds", source = "participantes", qualifiedByName = "pacientesToIds")
    @Mapping(target = "profissionaisParticipantesIds", source = "profissionaisParticipantes", qualifiedByName = "profissionaisToIds")
    EducacaoSaudeDTO toDTO(EducacaoSaude entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "estabelecimento", source = "estabelecimentoId", qualifiedByName = "estabelecimentoFromId")
    @Mapping(target = "profissionalResponsavel", source = "profissionalResponsavelId", qualifiedByName = "profissionalFromId")
    @Mapping(target = "equipeSaude", source = "equipeSaudeId", qualifiedByName = "equipeFromId")
    @Mapping(target = "tipoAtividade", source = "tipoAtividade", qualifiedByName = "tipoAtividadeFromCodigo")
    @Mapping(target = "participantes", source = "participantesIds", qualifiedByName = "pacientesFromIds")
    @Mapping(target = "profissionaisParticipantes", source = "profissionaisParticipantesIds", qualifiedByName = "profissionaisFromIds")
    EducacaoSaude fromRequest(EducacaoSaudeRequest request);

    @Mapping(target = "estabelecimentoId", source = "estabelecimento.id")
    @Mapping(target = "profissionalResponsavelId", source = "profissionalResponsavel.id")
    @Mapping(target = "equipeSaudeId", source = "equipeSaude.id")
    @Mapping(target = "tipoAtividade", source = "tipoAtividade.codigo")
    @Mapping(target = "tipoAtividadeDescricao", source = "tipoAtividade.descricao")
    @Mapping(target = "participantesIds", source = "participantes", qualifiedByName = "pacientesToIds")
    @Mapping(target = "profissionaisParticipantesIds", source = "profissionaisParticipantes", qualifiedByName = "profissionaisToIds")
    EducacaoSaudeResponse toResponse(EducacaoSaude entity);

    @Named("estabelecimentoFromId")
    default Estabelecimentos estabelecimentoFromId(UUID id) {
        if (id == null) return null;
        Estabelecimentos e = new Estabelecimentos();
        e.setId(id);
        return e;
    }

    @Named("profissionalFromId")
    default ProfissionaisSaude profissionalFromId(UUID id) {
        if (id == null) return null;
        ProfissionaisSaude p = new ProfissionaisSaude();
        p.setId(id);
        return p;
    }

    @Named("equipeFromId")
    default EquipeSaude equipeFromId(UUID id) {
        if (id == null) return null;
        EquipeSaude e = new EquipeSaude();
        e.setId(id);
        return e;
    }

    @Named("tipoAtividadeFromCodigo")
    default TipoEducacaoSaudeEnum tipoAtividadeFromCodigo(Integer codigo) {
        return TipoEducacaoSaudeEnum.fromCodigo(codigo);
    }

    @Named("pacientesFromIds")
    default List<Paciente> pacientesFromIds(List<UUID> ids) {
        if (ids == null) return new ArrayList<>();
        return ids.stream().map(id -> {
            Paciente p = new Paciente();
            p.setId(id);
            return p;
        }).collect(Collectors.toList());
    }

    @Named("pacientesToIds")
    default List<UUID> pacientesToIds(List<Paciente> pacientes) {
        if (pacientes == null) return new ArrayList<>();
        return pacientes.stream().map(Paciente::getId).collect(Collectors.toList());
    }

    @Named("profissionaisFromIds")
    default List<ProfissionaisSaude> profissionaisFromIds(List<UUID> ids) {
        if (ids == null) return new ArrayList<>();
        return ids.stream().map(id -> {
            ProfissionaisSaude p = new ProfissionaisSaude();
            p.setId(id);
            return p;
        }).collect(Collectors.toList());
    }

    @Named("profissionaisToIds")
    default List<UUID> profissionaisToIds(List<ProfissionaisSaude> profissionais) {
        if (profissionais == null) return new ArrayList<>();
        return profissionais.stream().map(ProfissionaisSaude::getId).collect(Collectors.toList());
    }
}

