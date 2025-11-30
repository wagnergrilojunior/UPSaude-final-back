package com.upsaude.mapper;

import com.upsaude.api.request.CatalogoProcedimentosRequest;
import com.upsaude.api.response.CatalogoProcedimentosResponse;
import com.upsaude.dto.CatalogoProcedimentosDTO;
import com.upsaude.entity.CatalogoProcedimentos;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MappingConfig.class)
public interface CatalogoProcedimentosMapper extends EntityMapper<CatalogoProcedimentos, CatalogoProcedimentosDTO> {

    @Mapping(target = "tenant", ignore = true)
    CatalogoProcedimentos toEntity(CatalogoProcedimentosDTO dto);

    CatalogoProcedimentosDTO toDTO(CatalogoProcedimentos entity);

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "estabelecimento", ignore = true)
    CatalogoProcedimentos fromRequest(CatalogoProcedimentosRequest request);

    CatalogoProcedimentosResponse toResponse(CatalogoProcedimentos entity);
}

