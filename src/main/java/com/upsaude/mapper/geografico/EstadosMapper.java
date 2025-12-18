package com.upsaude.mapper.geografico;

import com.upsaude.api.request.geografico.EstadosRequest;
import com.upsaude.api.response.geografico.EstadosResponse;
import com.upsaude.dto.EstadosDTO;
import com.upsaude.entity.geografico.Estados;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface EstadosMapper extends EntityMapper<Estados, EstadosDTO> {

    @Mapping(target = "active", ignore = true)
    Estados toEntity(EstadosDTO dto);

    EstadosDTO toDTO(Estados entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    Estados fromRequest(EstadosRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    void updateFromRequest(EstadosRequest request, @MappingTarget Estados entity);

    EstadosResponse toResponse(Estados entity);
}
