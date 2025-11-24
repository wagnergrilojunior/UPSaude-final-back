package com.upsaude.mapper;

import com.upsaude.api.request.VacinasRequest;
import com.upsaude.api.response.VacinasResponse;
import com.upsaude.dto.VacinasDTO;
import com.upsaude.entity.Vacinas;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MappingConfig.class)
public interface VacinasMapper extends EntityMapper<Vacinas, VacinasDTO> {

    @Mapping(target = "tenant", ignore = true)
    Vacinas toEntity(VacinasDTO dto);

    VacinasDTO toDTO(Vacinas entity);

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "active", ignore = true)
    Vacinas fromRequest(VacinasRequest request);

    VacinasResponse toResponse(Vacinas entity);
}

