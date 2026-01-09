package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.ContraindicacoesVacinaRequest;
import com.upsaude.api.response.embeddable.ContraindicacoesVacinaResponse;
import com.upsaude.entity.embeddable.ContraindicacoesVacina;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface ContraindicacoesVacinaMapper {
    ContraindicacoesVacina toEntity(ContraindicacoesVacinaRequest request);
    ContraindicacoesVacinaResponse toResponse(ContraindicacoesVacina entity);
    void updateFromRequest(ContraindicacoesVacinaRequest request, @MappingTarget ContraindicacoesVacina entity);

}
