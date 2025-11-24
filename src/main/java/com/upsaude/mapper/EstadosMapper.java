package com.upsaude.mapper;

import com.upsaude.api.request.EstadosRequest;
import com.upsaude.api.response.EstadosResponse;
import com.upsaude.dto.EstadosDTO;
import com.upsaude.entity.Estados;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MappingConfig.class)
public interface EstadosMapper extends EntityMapper<Estados, EstadosDTO> {

    @Mapping(target = "active", ignore = true)
    Estados toEntity(EstadosDTO dto);

    EstadosDTO toDTO(Estados entity);

    @Mapping(target = "active", ignore = true)
    Estados fromRequest(EstadosRequest request);

    EstadosResponse toResponse(Estados entity);
}

