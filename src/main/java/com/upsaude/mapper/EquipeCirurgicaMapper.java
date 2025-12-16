package com.upsaude.mapper;

import com.upsaude.api.request.EquipeCirurgicaRequest;
import com.upsaude.api.response.EquipeCirurgicaResponse;
import com.upsaude.dto.EquipeCirurgicaDTO;
import com.upsaude.entity.EquipeCirurgica;
import com.upsaude.entity.Cirurgia;
import com.upsaude.entity.Medicos;
import com.upsaude.entity.ProfissionaisSaude;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = {CirurgiaMapper.class, MedicosMapper.class, ProfissionaisSaudeMapper.class})
public interface EquipeCirurgicaMapper extends EntityMapper<EquipeCirurgica, EquipeCirurgicaDTO> {

    @Mapping(target = "active", ignore = true)
    EquipeCirurgica toEntity(EquipeCirurgicaDTO dto);

    EquipeCirurgicaDTO toDTO(EquipeCirurgica entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "cirurgia", ignore = true)
    @Mapping(target = "medico", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    EquipeCirurgica fromRequest(EquipeCirurgicaRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "cirurgia", ignore = true)
    @Mapping(target = "medico", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    void updateFromRequest(EquipeCirurgicaRequest request, @MappingTarget EquipeCirurgica entity);

    EquipeCirurgicaResponse toResponse(EquipeCirurgica entity);
}
