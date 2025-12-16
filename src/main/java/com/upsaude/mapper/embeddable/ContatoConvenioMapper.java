package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.ContatoConvenioRequest;
import com.upsaude.api.response.embeddable.ContatoConvenioResponse;
import com.upsaude.entity.embeddable.ContatoConvenio;
import com.upsaude.dto.embeddable.ContatoConvenioDTO;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface ContatoConvenioMapper {
    ContatoConvenio toEntity(ContatoConvenioRequest request);
    ContatoConvenioResponse toResponse(ContatoConvenio entity);
    void updateFromRequest(ContatoConvenioRequest request, @MappingTarget ContatoConvenio entity);

    ContatoConvenio toEntity(com.upsaude.dto.embeddable.ContatoConvenioDTO dto);
    com.upsaude.dto.embeddable.ContatoConvenioDTO toDTO(ContatoConvenio entity);
    void updateFromDTO(com.upsaude.dto.embeddable.ContatoConvenioDTO dto, @MappingTarget ContatoConvenio entity);
}
