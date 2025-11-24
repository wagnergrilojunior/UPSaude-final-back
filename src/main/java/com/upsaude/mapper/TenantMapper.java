package com.upsaude.mapper;

import com.upsaude.api.request.TenantRequest;
import com.upsaude.api.response.TenantResponse;
import com.upsaude.dto.TenantDTO;
import com.upsaude.entity.Tenant;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MappingConfig.class)
public interface TenantMapper extends EntityMapper<Tenant, TenantDTO> {

    @Mapping(target = "tenant", ignore = true)
    Tenant toEntity(TenantDTO dto);

    TenantDTO toDTO(Tenant entity);

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "active", ignore = true)
    Tenant fromRequest(TenantRequest request);

    TenantResponse toResponse(Tenant entity);
}

