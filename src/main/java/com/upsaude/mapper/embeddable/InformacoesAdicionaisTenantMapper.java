package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.InformacoesAdicionaisTenantRequest;
import com.upsaude.api.response.embeddable.InformacoesAdicionaisTenantResponse;
import com.upsaude.entity.embeddable.InformacoesAdicionaisTenant;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface InformacoesAdicionaisTenantMapper {
    InformacoesAdicionaisTenant fromRequest(InformacoesAdicionaisTenantRequest request);
    InformacoesAdicionaisTenantResponse toResponse(InformacoesAdicionaisTenant entity);
    void updateFromRequest(InformacoesAdicionaisTenantRequest request, @MappingTarget InformacoesAdicionaisTenant entity);
}

