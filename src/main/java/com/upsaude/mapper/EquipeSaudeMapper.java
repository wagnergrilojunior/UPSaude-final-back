package com.upsaude.mapper;

import com.upsaude.api.request.EquipeSaudeRequest;
import com.upsaude.api.response.EquipeSaudeResponse;
import com.upsaude.dto.EquipeSaudeDTO;
import com.upsaude.entity.Estabelecimentos;
import com.upsaude.entity.EquipeSaude;
import com.upsaude.entity.VinculoProfissionalEquipe;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(config = MappingConfig.class)
public interface EquipeSaudeMapper extends EntityMapper<EquipeSaude, EquipeSaudeDTO> {

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "estabelecimento", source = "estabelecimentoId", qualifiedByName = "estabelecimentoFromId")
    @Mapping(target = "vinculosProfissionais", ignore = true)
    EquipeSaude toEntity(EquipeSaudeDTO dto);

    @Mapping(target = "estabelecimentoId", source = "estabelecimento.id")
    EquipeSaudeDTO toDTO(EquipeSaude entity);

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "estabelecimento", source = "estabelecimentoId", qualifiedByName = "estabelecimentoFromId")
    @Mapping(target = "vinculosProfissionais", ignore = true)
    EquipeSaude fromRequest(EquipeSaudeRequest request);

    @Mapping(target = "estabelecimentoId", source = "estabelecimento.id")
    @Mapping(target = "estabelecimentoNome", source = "estabelecimento.nome")
    EquipeSaudeResponse toResponse(EquipeSaude entity);

    @Named("estabelecimentoFromId")
    default Estabelecimentos estabelecimentoFromId(UUID id) {
        if (id == null) return null;
        Estabelecimentos e = new Estabelecimentos();
        e.setId(id);
        return e;
    }

}

