package com.upsaude.mapper.profissional.equipe;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.upsaude.api.request.profissional.equipe.PlantaoRequest;
import com.upsaude.api.response.profissional.equipe.PlantaoResponse;
import com.upsaude.dto.profissional.equipe.PlantaoDTO;
import com.upsaude.entity.profissional.equipe.Plantao;
import com.upsaude.mapper.EntityMapper;
import com.upsaude.mapper.config.MappingConfig;
import com.upsaude.mapper.estabelecimento.EstabelecimentosMapper;
import com.upsaude.mapper.profissional.MedicosMapper;
import com.upsaude.mapper.profissional.ProfissionaisSaudeMapper;

@Mapper(config = MappingConfig.class, uses = {MedicosMapper.class, ProfissionaisSaudeMapper.class, EstabelecimentosMapper.class})
public interface PlantaoMapper extends EntityMapper<Plantao, PlantaoDTO> {

    @Mapping(target = "active", ignore = true)
    Plantao toEntity(PlantaoDTO dto);

    PlantaoDTO toDTO(Plantao entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "estabelecimento", ignore = true)
    @Mapping(target = "medico", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    Plantao fromRequest(PlantaoRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "estabelecimento", ignore = true)
    @Mapping(target = "medico", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    void updateFromRequest(PlantaoRequest request, @MappingTarget Plantao entity);

    PlantaoResponse toResponse(Plantao entity);
}
