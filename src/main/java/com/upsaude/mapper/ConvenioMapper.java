package com.upsaude.mapper;

import com.upsaude.api.request.ConvenioRequest;
import com.upsaude.api.response.ConvenioResponse;
import com.upsaude.dto.ConvenioDTO;
import com.upsaude.entity.Convenio;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MappingConfig.class)
public interface ConvenioMapper extends EntityMapper<Convenio, ConvenioDTO> {

    @Mapping(target = "tenant", ignore = true)
    Convenio toEntity(ConvenioDTO dto);

    ConvenioDTO toDTO(Convenio entity);

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "active", ignore = true)
    Convenio fromRequest(ConvenioRequest request);

    ConvenioResponse toResponse(Convenio entity);
}

