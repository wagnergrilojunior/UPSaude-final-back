package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.ContraindicacoesPrecaucoesMedicamentoRequest;
import com.upsaude.api.response.embeddable.ContraindicacoesPrecaucoesMedicamentoResponse;
import com.upsaude.entity.embeddable.ContraindicacoesPrecaucoesMedicamento;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface ContraindicacoesPrecaucoesMedicamentoMapper {
    ContraindicacoesPrecaucoesMedicamento toEntity(ContraindicacoesPrecaucoesMedicamentoRequest request);
    ContraindicacoesPrecaucoesMedicamentoResponse toResponse(ContraindicacoesPrecaucoesMedicamento entity);
    void updateFromRequest(ContraindicacoesPrecaucoesMedicamentoRequest request, @MappingTarget ContraindicacoesPrecaucoesMedicamento entity);

}
