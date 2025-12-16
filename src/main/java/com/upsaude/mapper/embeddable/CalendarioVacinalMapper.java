package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.CalendarioVacinalRequest;
import com.upsaude.api.response.embeddable.CalendarioVacinalResponse;
import com.upsaude.entity.embeddable.CalendarioVacinal;
import com.upsaude.dto.embeddable.CalendarioVacinalDTO;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface CalendarioVacinalMapper {
    CalendarioVacinal toEntity(CalendarioVacinalRequest request);
    CalendarioVacinalResponse toResponse(CalendarioVacinal entity);
    void updateFromRequest(CalendarioVacinalRequest request, @MappingTarget CalendarioVacinal entity);

    CalendarioVacinal toEntity(com.upsaude.dto.embeddable.CalendarioVacinalDTO dto);
    com.upsaude.dto.embeddable.CalendarioVacinalDTO toDTO(CalendarioVacinal entity);
    void updateFromDTO(com.upsaude.dto.embeddable.CalendarioVacinalDTO dto, @MappingTarget CalendarioVacinal entity);
}
