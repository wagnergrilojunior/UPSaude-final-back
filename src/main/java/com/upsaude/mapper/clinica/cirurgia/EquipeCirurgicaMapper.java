package com.upsaude.mapper.clinica.cirurgia;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.upsaude.api.request.clinica.cirurgia.EquipeCirurgicaRequest;
import com.upsaude.api.response.clinica.cirurgia.EquipeCirurgicaResponse;
import com.upsaude.dto.clinica.cirurgia.EquipeCirurgicaDTO;
import com.upsaude.entity.clinica.cirurgia.EquipeCirurgica;
import com.upsaude.mapper.EntityMapper;
import com.upsaude.mapper.config.MappingConfig;
import com.upsaude.mapper.profissional.MedicosMapper;
import com.upsaude.mapper.profissional.ProfissionaisSaudeMapper;

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
