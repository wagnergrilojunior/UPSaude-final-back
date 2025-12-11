package com.upsaude.mapper;

import com.upsaude.api.request.PlantaoRequest;
import com.upsaude.api.response.PlantaoResponse;
import com.upsaude.dto.PlantaoDTO;
import com.upsaude.entity.Plantao;
import com.upsaude.entity.Medicos;
import com.upsaude.entity.ProfissionaisSaude;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = {MedicosMapper.class, ProfissionaisSaudeMapper.class})
public interface PlantaoMapper extends EntityMapper<Plantao, PlantaoDTO> {

    @Mapping(target = "active", ignore = true)
    Plantao toEntity(PlantaoDTO dto);

    PlantaoDTO toDTO(Plantao entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "medico", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    Plantao fromRequest(PlantaoRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "medico", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    void updateFromRequest(PlantaoRequest request, @MappingTarget Plantao entity);

    PlantaoResponse toResponse(Plantao entity);
}
