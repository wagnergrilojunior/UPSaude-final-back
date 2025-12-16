package com.upsaude.mapper;

import com.upsaude.api.request.ProcedimentosOdontologicosRequest;
import com.upsaude.api.response.ProcedimentosOdontologicosResponse;
import com.upsaude.dto.ProcedimentosOdontologicosDTO;
import com.upsaude.entity.ProcedimentosOdontologicos;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface ProcedimentosOdontologicosMapper extends EntityMapper<ProcedimentosOdontologicos, ProcedimentosOdontologicosDTO> {

    @Mapping(target = "active", ignore = true)
    ProcedimentosOdontologicos toEntity(ProcedimentosOdontologicosDTO dto);

    ProcedimentosOdontologicosDTO toDTO(ProcedimentosOdontologicos entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    ProcedimentosOdontologicos fromRequest(ProcedimentosOdontologicosRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    void updateFromRequest(ProcedimentosOdontologicosRequest request, @MappingTarget ProcedimentosOdontologicos entity);

    ProcedimentosOdontologicosResponse toResponse(ProcedimentosOdontologicos entity);
}
