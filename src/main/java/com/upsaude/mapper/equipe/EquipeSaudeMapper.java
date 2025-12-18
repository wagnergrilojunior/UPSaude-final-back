package com.upsaude.mapper.equipe;

import com.upsaude.api.request.equipe.EquipeSaudeRequest;
import com.upsaude.api.response.equipe.EquipeSaudeResponse;
import com.upsaude.dto.EquipeSaudeDTO;
import com.upsaude.entity.equipe.EquipeSaude;
import com.upsaude.entity.estabelecimento.Estabelecimentos;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = {EstabelecimentosMapper.class})
public interface EquipeSaudeMapper extends EntityMapper<EquipeSaude, EquipeSaudeDTO> {

    @Mapping(target = "active", ignore = true)
    EquipeSaude toEntity(EquipeSaudeDTO dto);

    EquipeSaudeDTO toDTO(EquipeSaude entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "estabelecimento", ignore = true)
    @Mapping(target = "vinculosProfissionais", ignore = true)
    EquipeSaude fromRequest(EquipeSaudeRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "estabelecimento", ignore = true)
    @Mapping(target = "vinculosProfissionais", ignore = true)
    void updateFromRequest(EquipeSaudeRequest request, @MappingTarget EquipeSaude entity);

    EquipeSaudeResponse toResponse(EquipeSaude entity);
}
