package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.EficaciaVacinaRequest;
import com.upsaude.api.response.embeddable.EficaciaVacinaResponse;
import com.upsaude.entity.embeddable.EficaciaVacina;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface EficaciaVacinaMapper {
    EficaciaVacina toEntity(EficaciaVacinaRequest request);
    EficaciaVacinaResponse toResponse(EficaciaVacina entity);
    void updateFromRequest(EficaciaVacinaRequest request, @MappingTarget EficaciaVacina entity);

}
