package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.IntegracaoGovernamentalVacinaRequest;
import com.upsaude.api.response.embeddable.IntegracaoGovernamentalVacinaResponse;
import com.upsaude.entity.embeddable.IntegracaoGovernamentalVacina;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface IntegracaoGovernamentalVacinaMapper {
    IntegracaoGovernamentalVacina toEntity(IntegracaoGovernamentalVacinaRequest request);
    IntegracaoGovernamentalVacinaResponse toResponse(IntegracaoGovernamentalVacina entity);
    void updateFromRequest(IntegracaoGovernamentalVacinaRequest request, @MappingTarget IntegracaoGovernamentalVacina entity);

}
