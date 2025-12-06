package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.InformacoesFinanceirasConvenioRequest;
import com.upsaude.api.response.embeddable.InformacoesFinanceirasConvenioResponse;
import com.upsaude.entity.embeddable.InformacoesFinanceirasConvenio;
import com.upsaude.dto.embeddable.InformacoesFinanceirasConvenioDTO;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface InformacoesFinanceirasConvenioMapper {
    InformacoesFinanceirasConvenio toEntity(InformacoesFinanceirasConvenioRequest request);
    InformacoesFinanceirasConvenioResponse toResponse(InformacoesFinanceirasConvenio entity);
    void updateFromRequest(InformacoesFinanceirasConvenioRequest request, @MappingTarget InformacoesFinanceirasConvenio entity);

    // Mapeamento entre DTO e Entity
    InformacoesFinanceirasConvenio toEntity(com.upsaude.dto.embeddable.InformacoesFinanceirasConvenioDTO dto);
    com.upsaude.dto.embeddable.InformacoesFinanceirasConvenioDTO toDTO(InformacoesFinanceirasConvenio entity);
    void updateFromDTO(com.upsaude.dto.embeddable.InformacoesFinanceirasConvenioDTO dto, @MappingTarget InformacoesFinanceirasConvenio entity);
}
