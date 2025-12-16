package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.EsquemaVacinalRequest;
import com.upsaude.api.response.embeddable.EsquemaVacinalResponse;
import com.upsaude.entity.embeddable.EsquemaVacinal;
import com.upsaude.dto.embeddable.EsquemaVacinalDTO;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface EsquemaVacinalMapper {
    EsquemaVacinal toEntity(EsquemaVacinalRequest request);
    EsquemaVacinalResponse toResponse(EsquemaVacinal entity);
    void updateFromRequest(EsquemaVacinalRequest request, @MappingTarget EsquemaVacinal entity);

    EsquemaVacinal toEntity(com.upsaude.dto.embeddable.EsquemaVacinalDTO dto);
    com.upsaude.dto.embeddable.EsquemaVacinalDTO toDTO(EsquemaVacinal entity);
    void updateFromDTO(com.upsaude.dto.embeddable.EsquemaVacinalDTO dto, @MappingTarget EsquemaVacinal entity);
}
