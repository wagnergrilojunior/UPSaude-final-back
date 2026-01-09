package com.upsaude.mapper.paciente.deficiencia;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.upsaude.api.request.deficiencia.DeficienciasRequest;
import com.upsaude.api.response.deficiencia.DeficienciasResponse;
import com.upsaude.entity.paciente.deficiencia.Deficiencias;
import com.upsaude.mapper.config.MappingConfig;

@Mapper(config = MappingConfig.class)
public interface DeficienciasMapper  {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    Deficiencias fromRequest(DeficienciasRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    void updateFromRequest(DeficienciasRequest request, @MappingTarget Deficiencias entity);

    DeficienciasResponse toResponse(Deficiencias entity);
}
