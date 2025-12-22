package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.ContatoConvenioRequest;
import com.upsaude.api.response.embeddable.ContatoConvenioResponse;
import com.upsaude.entity.embeddable.ContatoConvenio;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface ContatoConvenioMapper {
    ContatoConvenio toEntity(ContatoConvenioRequest request);
    ContatoConvenioResponse toResponse(ContatoConvenio entity);
    void updateFromRequest(ContatoConvenioRequest request, @MappingTarget ContatoConvenio entity);

}
