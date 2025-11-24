package com.upsaude.mapper;

import com.upsaude.api.request.PapeisRequest;
import com.upsaude.api.response.PapeisResponse;
import com.upsaude.dto.PapeisDTO;
import com.upsaude.entity.Papeis;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MappingConfig.class)
public interface PapeisMapper extends EntityMapper<Papeis, PapeisDTO> {

    @Mapping(target = "active", ignore = true)
    Papeis toEntity(PapeisDTO dto);

    PapeisDTO toDTO(Papeis entity);

    @Mapping(target = "active", ignore = true)
    Papeis fromRequest(PapeisRequest request);

    PapeisResponse toResponse(Papeis entity);
}

