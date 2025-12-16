package com.upsaude.mapper;

import com.upsaude.api.request.DeficienciasRequest;
import com.upsaude.api.response.DeficienciasResponse;
import com.upsaude.dto.DeficienciasDTO;
import com.upsaude.entity.Deficiencias;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface DeficienciasMapper extends EntityMapper<Deficiencias, DeficienciasDTO> {

    @Mapping(target = "active", ignore = true)
    Deficiencias toEntity(DeficienciasDTO dto);

    DeficienciasDTO toDTO(Deficiencias entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    Deficiencias fromRequest(DeficienciasRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    void updateFromRequest(DeficienciasRequest request, @MappingTarget Deficiencias entity);

    DeficienciasResponse toResponse(Deficiencias entity);
}
