package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.DadosFiscaisTenantRequest;
import com.upsaude.api.response.embeddable.DadosFiscaisTenantResponse;
import com.upsaude.entity.embeddable.DadosFiscaisTenant;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface DadosFiscaisTenantMapper {
    DadosFiscaisTenant fromRequest(DadosFiscaisTenantRequest request);
    DadosFiscaisTenantResponse toResponse(DadosFiscaisTenant entity);
    void updateFromRequest(DadosFiscaisTenantRequest request, @MappingTarget DadosFiscaisTenant entity);
}
