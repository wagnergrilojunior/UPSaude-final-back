package com.upsaude.mapper;

import com.upsaude.api.request.PerfisUsuariosRequest;
import com.upsaude.api.response.PerfisUsuariosResponse;
import com.upsaude.dto.PerfisUsuariosDTO;
import com.upsaude.entity.PerfisUsuarios;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface PerfisUsuariosMapper extends EntityMapper<PerfisUsuarios, PerfisUsuariosDTO> {

    @Mapping(target = "active", ignore = true)
    PerfisUsuarios toEntity(PerfisUsuariosDTO dto);

    PerfisUsuariosDTO toDTO(PerfisUsuarios entity);

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
