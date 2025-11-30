package com.upsaude.mapper;

import com.upsaude.api.request.AcaoPromocaoPrevencaoRequest;
import com.upsaude.api.response.AcaoPromocaoPrevencaoResponse;
import com.upsaude.dto.AcaoPromocaoPrevencaoDTO;
import com.upsaude.entity.AcaoPromocaoPrevencao;
import com.upsaude.entity.Estabelecimentos;
import com.upsaude.entity.EquipeSaude;
import com.upsaude.entity.ProfissionaisSaude;
import com.upsaude.enums.TipoAcaoPromocaoSaudeEnum;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Mapper para conversão entre AcaoPromocaoPrevencao entity, DTO, Request e Response.
 *
 * @author UPSaúde
 */
@Mapper(config = MappingConfig.class)
public interface AcaoPromocaoPrevencaoMapper extends EntityMapper<AcaoPromocaoPrevencao, AcaoPromocaoPrevencaoDTO> {

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "estabelecimento", source = "estabelecimentoId", qualifiedByName = "estabelecimentoFromId")
    @Mapping(target = "profissionalResponsavel", source = "profissionalResponsavelId", qualifiedByName = "profissionalFromId")
    @Mapping(target = "equipeSaude", source = "equipeSaudeId", qualifiedByName = "equipeFromId")
    @Mapping(target = "tipoAcao", source = "tipoAcao", qualifiedByName = "tipoAcaoFromCodigo")
    @Mapping(target = "profissionaisParticipantes", source = "profissionaisParticipantesIds", qualifiedByName = "profissionaisFromIds")
    AcaoPromocaoPrevencao toEntity(AcaoPromocaoPrevencaoDTO dto);

    @Mapping(target = "estabelecimentoId", source = "estabelecimento.id")
    @Mapping(target = "profissionalResponsavelId", source = "profissionalResponsavel.id")
    @Mapping(target = "equipeSaudeId", source = "equipeSaude.id")
    @Mapping(target = "tipoAcao", source = "tipoAcao.codigo")
    @Mapping(target = "profissionaisParticipantesIds", source = "profissionaisParticipantes", qualifiedByName = "profissionaisToIds")
    AcaoPromocaoPrevencaoDTO toDTO(AcaoPromocaoPrevencao entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "estabelecimento", source = "estabelecimentoId", qualifiedByName = "estabelecimentoFromId")
    @Mapping(target = "profissionalResponsavel", source = "profissionalResponsavelId", qualifiedByName = "profissionalFromId")
    @Mapping(target = "equipeSaude", source = "equipeSaudeId", qualifiedByName = "equipeFromId")
    @Mapping(target = "tipoAcao", source = "tipoAcao", qualifiedByName = "tipoAcaoFromCodigo")
    @Mapping(target = "profissionaisParticipantes", source = "profissionaisParticipantesIds", qualifiedByName = "profissionaisFromIds")
    AcaoPromocaoPrevencao fromRequest(AcaoPromocaoPrevencaoRequest request);

    @Mapping(target = "estabelecimentoId", source = "estabelecimento.id")
    @Mapping(target = "profissionalResponsavelId", source = "profissionalResponsavel.id")
    @Mapping(target = "equipeSaudeId", source = "equipeSaude.id")
    @Mapping(target = "tipoAcao", source = "tipoAcao.codigo")
    @Mapping(target = "tipoAcaoDescricao", source = "tipoAcao.descricao")
    @Mapping(target = "profissionaisParticipantesIds", source = "profissionaisParticipantes", qualifiedByName = "profissionaisToIds")
    AcaoPromocaoPrevencaoResponse toResponse(AcaoPromocaoPrevencao entity);

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

    @Named("tipoAcaoFromCodigo")
    default TipoAcaoPromocaoSaudeEnum tipoAcaoFromCodigo(Integer codigo) {
        return TipoAcaoPromocaoSaudeEnum.fromCodigo(codigo);
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

