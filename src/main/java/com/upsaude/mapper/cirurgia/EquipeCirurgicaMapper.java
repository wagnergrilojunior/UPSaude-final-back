package com.upsaude.mapper.cirurgia;

import com.upsaude.api.request.cirurgia.EquipeCirurgicaRequest;
import com.upsaude.api.response.cirurgia.EquipeCirurgicaResponse;
import com.upsaude.dto.EquipeCirurgicaDTO;
import com.upsaude.entity.cirurgia.EquipeCirurgica;
import com.upsaude.entity.cirurgia.Cirurgia;
import com.upsaude.entity.profissional.Medicos;
import com.upsaude.entity.profissional.ProfissionaisSaude;
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
