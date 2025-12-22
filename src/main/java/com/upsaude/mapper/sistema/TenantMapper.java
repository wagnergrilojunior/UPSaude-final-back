package com.upsaude.mapper.sistema;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.upsaude.api.request.sistema.TenantRequest;
import com.upsaude.api.response.sistema.TenantResponse;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.mapper.config.MappingConfig;

@Mapper(config = MappingConfig.class)
public interface TenantMapper  {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "enderecos", ignore = true)
    Tenant fromRequest(TenantRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "enderecos", ignore = true)
    void updateFromRequest(TenantRequest request, @MappingTarget Tenant entity);

    TenantResponse toResponse(Tenant entity);
}
