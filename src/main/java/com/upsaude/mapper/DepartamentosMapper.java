package com.upsaude.mapper;

import com.upsaude.api.request.DepartamentosRequest;
import com.upsaude.api.response.DepartamentosResponse;
import com.upsaude.dto.DepartamentosDTO;
import com.upsaude.entity.Departamentos;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MappingConfig.class)
public interface DepartamentosMapper extends EntityMapper<Departamentos, DepartamentosDTO> {

    @Mapping(target = "tenant", ignore = true)
    Departamentos toEntity(DepartamentosDTO dto);

    DepartamentosDTO toDTO(Departamentos entity);

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "active", ignore = true)
    Departamentos fromRequest(DepartamentosRequest request);

    DepartamentosResponse toResponse(Departamentos entity);
}

