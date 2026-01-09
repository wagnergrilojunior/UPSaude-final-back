package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.ContatoTenantRequest;
import com.upsaude.api.response.embeddable.ContatoTenantResponse;
import com.upsaude.entity.embeddable.ContatoTenant;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface ContatoTenantMapper {
    ContatoTenant fromRequest(ContatoTenantRequest request);
    ContatoTenantResponse toResponse(ContatoTenant entity);
    void updateFromRequest(ContatoTenantRequest request, @MappingTarget ContatoTenant entity);
}
