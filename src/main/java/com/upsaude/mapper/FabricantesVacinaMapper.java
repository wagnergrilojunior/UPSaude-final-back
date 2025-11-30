package com.upsaude.mapper;

import com.upsaude.api.request.FabricantesVacinaRequest;
import com.upsaude.api.response.FabricantesVacinaResponse;
import com.upsaude.dto.FabricantesVacinaDTO;
import com.upsaude.entity.FabricantesVacina;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MappingConfig.class)
public interface FabricantesVacinaMapper extends EntityMapper<FabricantesVacina, FabricantesVacinaDTO> {

    FabricantesVacina toEntity(FabricantesVacinaDTO dto);

    FabricantesVacinaDTO toDTO(FabricantesVacina entity);

    @Mapping(target = "active", ignore = true)
    FabricantesVacina fromRequest(FabricantesVacinaRequest request);

    FabricantesVacinaResponse toResponse(FabricantesVacina entity);
}

