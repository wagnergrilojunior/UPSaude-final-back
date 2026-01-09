package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.ComposicaoVacinaRequest;
import com.upsaude.api.response.embeddable.ComposicaoVacinaResponse;
import com.upsaude.entity.embeddable.ComposicaoVacina;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface ComposicaoVacinaMapper {
    ComposicaoVacina toEntity(ComposicaoVacinaRequest request);
    ComposicaoVacinaResponse toResponse(ComposicaoVacina entity);
    void updateFromRequest(ComposicaoVacinaRequest request, @MappingTarget ComposicaoVacina entity);

}
