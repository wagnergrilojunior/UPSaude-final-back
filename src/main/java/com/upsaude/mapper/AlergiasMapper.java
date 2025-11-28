package com.upsaude.mapper;

import com.upsaude.api.request.AlergiasRequest;
import com.upsaude.api.response.AlergiasResponse;
import com.upsaude.dto.AlergiasDTO;
import com.upsaude.entity.Alergias;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MappingConfig.class)
public interface AlergiasMapper extends EntityMapper<Alergias, AlergiasDTO> {

    @Mapping(target = "tenant", ignore = true)
    Alergias toEntity(AlergiasDTO dto);

    AlergiasDTO toDTO(Alergias entity);

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "active", ignore = true)
    Alergias fromRequest(AlergiasRequest request);

    AlergiasResponse toResponse(Alergias entity);
}
