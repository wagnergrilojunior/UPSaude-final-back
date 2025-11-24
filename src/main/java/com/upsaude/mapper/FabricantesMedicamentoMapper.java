package com.upsaude.mapper;

import com.upsaude.api.request.FabricantesMedicamentoRequest;
import com.upsaude.api.response.FabricantesMedicamentoResponse;
import com.upsaude.dto.FabricantesMedicamentoDTO;
import com.upsaude.entity.FabricantesMedicamento;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MappingConfig.class)
public interface FabricantesMedicamentoMapper extends EntityMapper<FabricantesMedicamento, FabricantesMedicamentoDTO> {

    @Mapping(target = "tenant", ignore = true)
    FabricantesMedicamento toEntity(FabricantesMedicamentoDTO dto);

    FabricantesMedicamentoDTO toDTO(FabricantesMedicamento entity);

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "active", ignore = true)
    FabricantesMedicamento fromRequest(FabricantesMedicamentoRequest request);

    FabricantesMedicamentoResponse toResponse(FabricantesMedicamento entity);
}

