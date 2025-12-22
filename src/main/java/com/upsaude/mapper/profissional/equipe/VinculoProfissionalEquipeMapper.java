package com.upsaude.mapper.profissional.equipe;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.upsaude.api.request.profissional.equipe.VinculoProfissionalEquipeRequest;
import com.upsaude.api.response.profissional.equipe.VinculoProfissionalEquipeResponse;
import com.upsaude.entity.profissional.equipe.VinculoProfissionalEquipe;
import com.upsaude.mapper.config.MappingConfig;
import com.upsaude.mapper.profissional.ProfissionaisSaudeMapper;

@Mapper(config = MappingConfig.class, uses = {EquipeSaudeMapper.class, ProfissionaisSaudeMapper.class})
public interface VinculoProfissionalEquipeMapper  {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "equipe", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    VinculoProfissionalEquipe fromRequest(VinculoProfissionalEquipeRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "equipe", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    void updateFromRequest(VinculoProfissionalEquipeRequest request, @MappingTarget VinculoProfissionalEquipe entity);

    VinculoProfissionalEquipeResponse toResponse(VinculoProfissionalEquipe entity);
}
