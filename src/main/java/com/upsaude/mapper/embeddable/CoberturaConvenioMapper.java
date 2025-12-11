package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.CoberturaConvenioRequest;
import com.upsaude.api.response.embeddable.CoberturaConvenioResponse;
import com.upsaude.entity.embeddable.CoberturaConvenio;
import com.upsaude.dto.embeddable.CoberturaConvenioDTO;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface CoberturaConvenioMapper {
    CoberturaConvenio toEntity(CoberturaConvenioRequest request);
    CoberturaConvenioResponse toResponse(CoberturaConvenio entity);
    void updateFromRequest(CoberturaConvenioRequest request, @MappingTarget CoberturaConvenio entity);

    CoberturaConvenio toEntity(com.upsaude.dto.embeddable.CoberturaConvenioDTO dto);
    com.upsaude.dto.embeddable.CoberturaConvenioDTO toDTO(CoberturaConvenio entity);
    void updateFromDTO(com.upsaude.dto.embeddable.CoberturaConvenioDTO dto, @MappingTarget CoberturaConvenio entity);
}
