package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.ResponsavelTenantRequest;
import com.upsaude.api.response.embeddable.ResponsavelTenantResponse;
import com.upsaude.entity.embeddable.ResponsavelTenant;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface ResponsavelTenantMapper {
    ResponsavelTenant fromRequest(ResponsavelTenantRequest request);
    ResponsavelTenantResponse toResponse(ResponsavelTenant entity);
    void updateFromRequest(ResponsavelTenantRequest request, @MappingTarget ResponsavelTenant entity);
}

