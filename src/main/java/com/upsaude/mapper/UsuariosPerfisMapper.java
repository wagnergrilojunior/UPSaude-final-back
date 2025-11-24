package com.upsaude.mapper;

import com.upsaude.api.request.UsuariosPerfisRequest;
import com.upsaude.api.response.UsuariosPerfisResponse;
import com.upsaude.dto.UsuariosPerfisDTO;
import com.upsaude.entity.UsuariosPerfis;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MappingConfig.class)
public interface UsuariosPerfisMapper extends EntityMapper<UsuariosPerfis, UsuariosPerfisDTO> {

    @Mapping(target = "tenant", ignore = true)
    UsuariosPerfis toEntity(UsuariosPerfisDTO dto);

    UsuariosPerfisDTO toDTO(UsuariosPerfis entity);

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "active", ignore = true)
    UsuariosPerfis fromRequest(UsuariosPerfisRequest request);

    UsuariosPerfisResponse toResponse(UsuariosPerfis entity);
}

