package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.IdadeAplicacaoVacinaRequest;
import com.upsaude.api.response.embeddable.IdadeAplicacaoVacinaResponse;
import com.upsaude.entity.embeddable.IdadeAplicacaoVacina;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface IdadeAplicacaoVacinaMapper {
    IdadeAplicacaoVacina toEntity(IdadeAplicacaoVacinaRequest request);
    IdadeAplicacaoVacinaResponse toResponse(IdadeAplicacaoVacina entity);
    void updateFromRequest(IdadeAplicacaoVacinaRequest request, @MappingTarget IdadeAplicacaoVacina entity);

}
