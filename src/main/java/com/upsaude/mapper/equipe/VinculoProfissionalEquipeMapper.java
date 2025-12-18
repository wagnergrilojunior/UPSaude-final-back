package com.upsaude.mapper.equipe;

import com.upsaude.api.request.equipe.VinculoProfissionalEquipeRequest;
import com.upsaude.api.response.equipe.VinculoProfissionalEquipeResponse;
import com.upsaude.dto.VinculoProfissionalEquipeDTO;
import com.upsaude.entity.equipe.VinculoProfissionalEquipe;
import com.upsaude.entity.equipe.EquipeSaude;
import com.upsaude.entity.profissional.ProfissionaisSaude;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = {EquipeSaudeMapper.class, ProfissionaisSaudeMapper.class})
public interface VinculoProfissionalEquipeMapper extends EntityMapper<VinculoProfissionalEquipe, VinculoProfissionalEquipeDTO> {

    @Mapping(target = "active", ignore = true)
    VinculoProfissionalEquipe toEntity(VinculoProfissionalEquipeDTO dto);

    VinculoProfissionalEquipeDTO toDTO(VinculoProfissionalEquipe entity);

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
