package com.upsaude.mapper.fabricante;

import com.upsaude.api.request.fabricante.FabricantesMedicamentoRequest;
import com.upsaude.api.response.fabricante.FabricantesMedicamentoResponse;
import com.upsaude.dto.FabricantesMedicamentoDTO;
import com.upsaude.entity.fabricante.FabricantesMedicamento;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface FabricantesMedicamentoMapper extends EntityMapper<FabricantesMedicamento, FabricantesMedicamentoDTO> {

    @Mapping(target = "active", ignore = true)
    FabricantesMedicamento toEntity(FabricantesMedicamentoDTO dto);

    FabricantesMedicamentoDTO toDTO(FabricantesMedicamento entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    FabricantesMedicamento fromRequest(FabricantesMedicamentoRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    void updateFromRequest(FabricantesMedicamentoRequest request, @MappingTarget FabricantesMedicamento entity);

    FabricantesMedicamentoResponse toResponse(FabricantesMedicamento entity);
}
