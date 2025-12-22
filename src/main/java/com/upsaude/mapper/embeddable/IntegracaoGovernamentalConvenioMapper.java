package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.IntegracaoGovernamentalConvenioRequest;
import com.upsaude.api.response.embeddable.IntegracaoGovernamentalConvenioResponse;
import com.upsaude.entity.embeddable.IntegracaoGovernamentalConvenio;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface IntegracaoGovernamentalConvenioMapper {
    IntegracaoGovernamentalConvenio toEntity(IntegracaoGovernamentalConvenioRequest request);
    IntegracaoGovernamentalConvenioResponse toResponse(IntegracaoGovernamentalConvenio entity);
    void updateFromRequest(IntegracaoGovernamentalConvenioRequest request, @MappingTarget IntegracaoGovernamentalConvenio entity);

}
