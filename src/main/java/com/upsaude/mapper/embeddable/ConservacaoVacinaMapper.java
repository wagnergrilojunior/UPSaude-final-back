package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.ConservacaoVacinaRequest;
import com.upsaude.api.response.embeddable.ConservacaoVacinaResponse;
import com.upsaude.entity.embeddable.ConservacaoVacina;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface ConservacaoVacinaMapper {
    ConservacaoVacina toEntity(ConservacaoVacinaRequest request);
    ConservacaoVacinaResponse toResponse(ConservacaoVacina entity);
    void updateFromRequest(ConservacaoVacinaRequest request, @MappingTarget ConservacaoVacina entity);

}
