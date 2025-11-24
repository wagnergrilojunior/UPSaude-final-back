package com.upsaude.mapper;

import com.upsaude.api.request.ExamesRequest;
import com.upsaude.api.response.ExamesResponse;
import com.upsaude.dto.ExamesDTO;
import com.upsaude.entity.Exames;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MappingConfig.class)
public interface ExamesMapper extends EntityMapper<Exames, ExamesDTO> {

    @Mapping(target = "tenant", ignore = true)
    Exames toEntity(ExamesDTO dto);

    ExamesDTO toDTO(Exames entity);

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "active", ignore = true)
    Exames fromRequest(ExamesRequest request);

    ExamesResponse toResponse(Exames entity);
}

