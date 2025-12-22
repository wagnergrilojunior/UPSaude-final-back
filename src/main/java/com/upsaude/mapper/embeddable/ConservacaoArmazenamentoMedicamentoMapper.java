package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.ConservacaoArmazenamentoMedicamentoRequest;
import com.upsaude.api.response.embeddable.ConservacaoArmazenamentoMedicamentoResponse;
import com.upsaude.entity.embeddable.ConservacaoArmazenamentoMedicamento;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface ConservacaoArmazenamentoMedicamentoMapper {
    ConservacaoArmazenamentoMedicamento toEntity(ConservacaoArmazenamentoMedicamentoRequest request);
    ConservacaoArmazenamentoMedicamentoResponse toResponse(ConservacaoArmazenamentoMedicamento entity);
    void updateFromRequest(ConservacaoArmazenamentoMedicamentoRequest request, @MappingTarget ConservacaoArmazenamentoMedicamento entity);

}
