package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.TratamentoPadraoDoencaRequest;
import com.upsaude.api.response.embeddable.TratamentoPadraoDoencaResponse;
import com.upsaude.entity.embeddable.TratamentoPadraoDoenca;
import com.upsaude.dto.embeddable.TratamentoPadraoDoencaDTO;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface TratamentoPadraoDoencaMapper {
    TratamentoPadraoDoenca toEntity(TratamentoPadraoDoencaRequest request);
    TratamentoPadraoDoencaResponse toResponse(TratamentoPadraoDoenca entity);
    void updateFromRequest(TratamentoPadraoDoencaRequest request, @MappingTarget TratamentoPadraoDoenca entity);

    TratamentoPadraoDoenca toEntity(com.upsaude.dto.embeddable.TratamentoPadraoDoencaDTO dto);
    com.upsaude.dto.embeddable.TratamentoPadraoDoencaDTO toDTO(TratamentoPadraoDoenca entity);
    void updateFromDTO(com.upsaude.dto.embeddable.TratamentoPadraoDoencaDTO dto, @MappingTarget TratamentoPadraoDoenca entity);
}
