package com.upsaude.mapper;

import com.upsaude.api.request.CatalogoExamesRequest;
import com.upsaude.api.response.CatalogoExamesResponse;
import com.upsaude.dto.CatalogoExamesDTO;
import com.upsaude.entity.CatalogoExames;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MappingConfig.class)
public interface CatalogoExamesMapper extends EntityMapper<CatalogoExames, CatalogoExamesDTO> {

    @Mapping(target = "tenant", ignore = true)
    CatalogoExames toEntity(CatalogoExamesDTO dto);

    CatalogoExamesDTO toDTO(CatalogoExames entity);

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "estabelecimento", ignore = true)
    CatalogoExames fromRequest(CatalogoExamesRequest request);

    CatalogoExamesResponse toResponse(CatalogoExames entity);
}

