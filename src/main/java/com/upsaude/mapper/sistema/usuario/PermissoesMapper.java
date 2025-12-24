package com.upsaude.mapper.sistema.usuario;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.upsaude.api.request.sistema.usuario.PermissoesRequest;
import com.upsaude.api.response.sistema.usuario.PermissoesResponse;
import com.upsaude.entity.sistema.usuario.Permissoes;
import com.upsaude.mapper.config.MappingConfig;

@Mapper(config = MappingConfig.class)
public interface PermissoesMapper  {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    Permissoes fromRequest(PermissoesRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    void updateFromRequest(PermissoesRequest request, @MappingTarget Permissoes entity);

    PermissoesResponse toResponse(Permissoes entity);
}
