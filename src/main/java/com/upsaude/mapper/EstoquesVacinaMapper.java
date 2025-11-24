package com.upsaude.mapper;

import com.upsaude.api.request.EstoquesVacinaRequest;
import com.upsaude.api.response.EstoquesVacinaResponse;
import com.upsaude.dto.EstoquesVacinaDTO;
import com.upsaude.entity.EstoquesVacina;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MappingConfig.class)
public interface EstoquesVacinaMapper extends EntityMapper<EstoquesVacina, EstoquesVacinaDTO> {

    @Mapping(target = "tenant", ignore = true)
    EstoquesVacina toEntity(EstoquesVacinaDTO dto);

    EstoquesVacinaDTO toDTO(EstoquesVacina entity);

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "active", ignore = true)
    EstoquesVacina fromRequest(EstoquesVacinaRequest request);

    EstoquesVacinaResponse toResponse(EstoquesVacina entity);
}

