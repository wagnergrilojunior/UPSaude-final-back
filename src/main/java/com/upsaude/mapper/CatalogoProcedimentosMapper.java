package com.upsaude.mapper;

import com.upsaude.api.request.CatalogoProcedimentosRequest;
import com.upsaude.api.response.CatalogoProcedimentosResponse;
import com.upsaude.dto.CatalogoProcedimentosDTO;
import com.upsaude.entity.CatalogoProcedimentos;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface CatalogoProcedimentosMapper extends EntityMapper<CatalogoProcedimentos, CatalogoProcedimentosDTO> {

    @Mapping(target = "active", ignore = true)
    CatalogoProcedimentos toEntity(CatalogoProcedimentosDTO dto);

    CatalogoProcedimentosDTO toDTO(CatalogoProcedimentos entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    CatalogoProcedimentos fromRequest(CatalogoProcedimentosRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    void updateFromRequest(CatalogoProcedimentosRequest request, @MappingTarget CatalogoProcedimentos entity);

    CatalogoProcedimentosResponse toResponse(CatalogoProcedimentos entity);
}
