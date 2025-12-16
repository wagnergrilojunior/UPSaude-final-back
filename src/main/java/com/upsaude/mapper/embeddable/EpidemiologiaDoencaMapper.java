package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.EpidemiologiaDoencaRequest;
import com.upsaude.api.response.embeddable.EpidemiologiaDoencaResponse;
import com.upsaude.entity.embeddable.EpidemiologiaDoenca;
import com.upsaude.dto.embeddable.EpidemiologiaDoencaDTO;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface EpidemiologiaDoencaMapper {
    EpidemiologiaDoenca toEntity(EpidemiologiaDoencaRequest request);
    EpidemiologiaDoencaResponse toResponse(EpidemiologiaDoenca entity);
    void updateFromRequest(EpidemiologiaDoencaRequest request, @MappingTarget EpidemiologiaDoenca entity);

    EpidemiologiaDoenca toEntity(com.upsaude.dto.embeddable.EpidemiologiaDoencaDTO dto);
    com.upsaude.dto.embeddable.EpidemiologiaDoencaDTO toDTO(EpidemiologiaDoenca entity);
    void updateFromDTO(com.upsaude.dto.embeddable.EpidemiologiaDoencaDTO dto, @MappingTarget EpidemiologiaDoenca entity);
}
