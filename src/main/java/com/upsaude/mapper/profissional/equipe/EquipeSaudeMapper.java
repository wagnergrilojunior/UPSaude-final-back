package com.upsaude.mapper.profissional.equipe;

import com.upsaude.api.request.profissional.equipe.EquipeSaudeRequest;
import com.upsaude.api.response.profissional.equipe.EquipeSaudeResponse;
import com.upsaude.entity.profissional.equipe.EquipeSaude;
import com.upsaude.mapper.config.MappingConfig;
import com.upsaude.mapper.profissional.ProfissionaisSaudeMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = {ProfissionaisSaudeMapper.class})
public interface EquipeSaudeMapper  {

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

    @Mapping(target = "estabelecimento", ignore = true)
    EquipeSaudeResponse toResponse(EquipeSaude entity);
}
