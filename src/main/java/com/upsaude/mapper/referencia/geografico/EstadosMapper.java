package com.upsaude.mapper.referencia.geografico;

import com.upsaude.api.request.referencia.geografico.EstadosRequest;
import com.upsaude.api.response.referencia.geografico.EstadosResponse;
import com.upsaude.entity.referencia.geografico.Estados;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface EstadosMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    Estados fromRequest(EstadosRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    void updateFromRequest(EstadosRequest request, @MappingTarget Estados entity);

    EstadosResponse toResponse(Estados entity);
}
