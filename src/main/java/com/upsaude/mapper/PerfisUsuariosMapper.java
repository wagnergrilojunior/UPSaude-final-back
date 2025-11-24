package com.upsaude.mapper;

import com.upsaude.api.request.PerfisUsuariosRequest;
import com.upsaude.api.response.PerfisUsuariosResponse;
import com.upsaude.dto.PerfisUsuariosDTO;
import com.upsaude.entity.PerfisUsuarios;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MappingConfig.class)
public interface PerfisUsuariosMapper extends EntityMapper<PerfisUsuarios, PerfisUsuariosDTO> {

    @Mapping(target = "tenant", ignore = true)
    PerfisUsuarios toEntity(PerfisUsuariosDTO dto);

    PerfisUsuariosDTO toDTO(PerfisUsuarios entity);

    @Mapping(target = "tenant", ignore = true)
    PerfisUsuarios fromRequest(PerfisUsuariosRequest request);

    PerfisUsuariosResponse toResponse(PerfisUsuarios entity);
}

