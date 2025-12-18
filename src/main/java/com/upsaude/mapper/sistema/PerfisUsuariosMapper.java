package com.upsaude.mapper.sistema;

import com.upsaude.api.request.sistema.PerfisUsuariosRequest;
import com.upsaude.api.response.sistema.PerfisUsuariosResponse;
import com.upsaude.entity.sistema.PerfisUsuarios;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface PerfisUsuariosMapper {

    @Mapping(target = "active", ignore = true)
    PerfisUsuarios toEntity(PerfisUsuariosResponse dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    PerfisUsuarios fromRequest(PerfisUsuariosRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    void updateFromRequest(PerfisUsuariosRequest request, @MappingTarget PerfisUsuarios entity);

    PerfisUsuariosResponse toResponse(PerfisUsuarios entity);
}
