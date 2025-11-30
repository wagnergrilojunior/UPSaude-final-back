package com.upsaude.mapper;

import com.upsaude.api.request.PreNatalRequest;
import com.upsaude.api.response.PreNatalResponse;
import com.upsaude.dto.PreNatalDTO;
import com.upsaude.entity.Estabelecimentos;
import com.upsaude.entity.EquipeSaude;
import com.upsaude.entity.Paciente;
import com.upsaude.entity.PreNatal;
import com.upsaude.entity.ProfissionaisSaude;
import com.upsaude.enums.ClassificacaoRiscoGestacionalEnum;
import com.upsaude.enums.StatusPreNatalEnum;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

/**
 * Mapper para conversão entre PreNatal entity, DTO, Request e Response.
 *
 * @author UPSaúde
 */
@Mapper(config = MappingConfig.class)
public interface PreNatalMapper extends EntityMapper<PreNatal, PreNatalDTO> {

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "estabelecimento", source = "estabelecimentoId", qualifiedByName = "estabelecimentoFromId")
    @Mapping(target = "paciente", source = "pacienteId", qualifiedByName = "pacienteFromId")
    @Mapping(target = "profissionalResponsavel", source = "profissionalResponsavelId", qualifiedByName = "profissionalFromId")
    @Mapping(target = "equipeSaude", source = "equipeSaudeId", qualifiedByName = "equipeFromId")
    @Mapping(target = "classificacaoRisco", source = "classificacaoRisco", qualifiedByName = "classificacaoRiscoFromCodigo")
    @Mapping(target = "statusPreNatal", source = "statusPreNatal", qualifiedByName = "statusFromCodigo")
    PreNatal toEntity(PreNatalDTO dto);

    @Mapping(target = "estabelecimentoId", source = "estabelecimento.id")
    @Mapping(target = "pacienteId", source = "paciente.id")
    @Mapping(target = "profissionalResponsavelId", source = "profissionalResponsavel.id")
    @Mapping(target = "equipeSaudeId", source = "equipeSaude.id")
    @Mapping(target = "classificacaoRisco", source = "classificacaoRisco.codigo")
    @Mapping(target = "statusPreNatal", source = "statusPreNatal.codigo")
    PreNatalDTO toDTO(PreNatal entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "estabelecimento", source = "estabelecimentoId", qualifiedByName = "estabelecimentoFromId")
    @Mapping(target = "paciente", source = "pacienteId", qualifiedByName = "pacienteFromId")
    @Mapping(target = "profissionalResponsavel", source = "profissionalResponsavelId", qualifiedByName = "profissionalFromId")
    @Mapping(target = "equipeSaude", source = "equipeSaudeId", qualifiedByName = "equipeFromId")
    @Mapping(target = "classificacaoRisco", source = "classificacaoRisco", qualifiedByName = "classificacaoRiscoFromCodigo")
    @Mapping(target = "statusPreNatal", source = "statusPreNatal", qualifiedByName = "statusFromCodigo")
    PreNatal fromRequest(PreNatalRequest request);

    @Mapping(target = "estabelecimentoId", source = "estabelecimento.id")
    @Mapping(target = "pacienteId", source = "paciente.id")
    @Mapping(target = "profissionalResponsavelId", source = "profissionalResponsavel.id")
    @Mapping(target = "equipeSaudeId", source = "equipeSaude.id")
    @Mapping(target = "classificacaoRisco", source = "classificacaoRisco.codigo")
    @Mapping(target = "classificacaoRiscoDescricao", source = "classificacaoRisco.descricao")
    @Mapping(target = "statusPreNatal", source = "statusPreNatal.codigo")
    @Mapping(target = "statusPreNatalDescricao", source = "statusPreNatal.descricao")
    PreNatalResponse toResponse(PreNatal entity);

    @Named("estabelecimentoFromId")
    default Estabelecimentos estabelecimentoFromId(UUID id) {
        if (id == null) return null;
        Estabelecimentos e = new Estabelecimentos();
        e.setId(id);
        return e;
    }

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

    @Named("equipeFromId")
    default EquipeSaude equipeFromId(UUID id) {
        if (id == null) return null;
        EquipeSaude e = new EquipeSaude();
        e.setId(id);
        return e;
    }

    @Named("classificacaoRiscoFromCodigo")
    default ClassificacaoRiscoGestacionalEnum classificacaoRiscoFromCodigo(Integer codigo) {
        return ClassificacaoRiscoGestacionalEnum.fromCodigo(codigo);
    }

    @Named("statusFromCodigo")
    default StatusPreNatalEnum statusFromCodigo(Integer codigo) {
        return StatusPreNatalEnum.fromCodigo(codigo);
    }
}

