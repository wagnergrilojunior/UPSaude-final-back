package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.EficaciaVacinaRequest;
import com.upsaude.api.response.embeddable.EficaciaVacinaResponse;
import com.upsaude.entity.embeddable.EficaciaVacina;
import com.upsaude.dto.embeddable.EficaciaVacinaDTO;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface EficaciaVacinaMapper {
    EficaciaVacina toEntity(EficaciaVacinaRequest request);
    EficaciaVacinaResponse toResponse(EficaciaVacina entity);
    void updateFromRequest(EficaciaVacinaRequest request, @MappingTarget EficaciaVacina entity);

    EficaciaVacina toEntity(com.upsaude.dto.embeddable.EficaciaVacinaDTO dto);
    com.upsaude.dto.embeddable.EficaciaVacinaDTO toDTO(EficaciaVacina entity);
    void updateFromDTO(com.upsaude.dto.embeddable.EficaciaVacinaDTO dto, @MappingTarget EficaciaVacina entity);
}
