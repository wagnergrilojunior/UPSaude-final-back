package com.upsaude.mapper;

import com.upsaude.api.request.ControlePontoRequest;
import com.upsaude.api.response.ControlePontoResponse;
import com.upsaude.dto.ControlePontoDTO;
import com.upsaude.entity.ControlePonto;
import com.upsaude.entity.Medicos;
import com.upsaude.entity.ProfissionaisSaude;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

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
