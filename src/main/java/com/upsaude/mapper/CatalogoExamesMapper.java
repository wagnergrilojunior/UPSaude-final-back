package com.upsaude.mapper;

import com.upsaude.api.request.CatalogoExamesRequest;
import com.upsaude.api.response.CatalogoExamesResponse;
import com.upsaude.dto.CatalogoExamesDTO;
import com.upsaude.entity.CatalogoExames;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface CatalogoExamesMapper extends EntityMapper<CatalogoExames, CatalogoExamesDTO> {

    @Mapping(target = "active", ignore = true)
    CatalogoExames toEntity(CatalogoExamesDTO dto);

    CatalogoExamesDTO toDTO(CatalogoExames entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    CatalogoExames fromRequest(CatalogoExamesRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    void updateFromRequest(CatalogoExamesRequest request, @MappingTarget CatalogoExames entity);

    CatalogoExamesResponse toResponse(CatalogoExames entity);
}
