package com.upsaude.mapper.profissional.equipe;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.upsaude.api.request.profissional.equipe.ControlePontoRequest;
import com.upsaude.api.response.profissional.equipe.ControlePontoResponse;
import com.upsaude.dto.profissional.equipe.ControlePontoDTO;
import com.upsaude.entity.profissional.equipe.ControlePonto;
import com.upsaude.mapper.EntityMapper;
import com.upsaude.mapper.config.MappingConfig;
import com.upsaude.mapper.profissional.MedicosMapper;
import com.upsaude.mapper.profissional.ProfissionaisSaudeMapper;

@Mapper(config = MappingConfig.class, uses = {MedicosMapper.class, ProfissionaisSaudeMapper.class})
public interface ControlePontoMapper extends EntityMapper<ControlePonto, ControlePontoDTO> {

    @Mapping(target = "active", ignore = true)
    ControlePonto toEntity(ControlePontoDTO dto);

    ControlePontoDTO toDTO(ControlePonto entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "medico", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    ControlePonto fromRequest(ControlePontoRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "medico", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    void updateFromRequest(ControlePontoRequest request, @MappingTarget ControlePonto entity);

    ControlePontoResponse toResponse(ControlePonto entity);
}
