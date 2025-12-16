package com.upsaude.mapper;

import com.upsaude.api.request.TenantRequest;
import com.upsaude.api.response.TenantResponse;
import com.upsaude.dto.TenantDTO;
import com.upsaude.entity.Tenant;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface TenantMapper extends EntityMapper<Tenant, TenantDTO> {

    @Mapping(target = "active", ignore = true)
    Tenant toEntity(TenantDTO dto);

    TenantDTO toDTO(Tenant entity);

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
