package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.ConservacaoArmazenamentoMedicamentoRequest;
import com.upsaude.api.response.embeddable.ConservacaoArmazenamentoMedicamentoResponse;
import com.upsaude.entity.embeddable.ConservacaoArmazenamentoMedicamento;
import com.upsaude.dto.embeddable.ConservacaoArmazenamentoMedicamentoDTO;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface ConservacaoArmazenamentoMedicamentoMapper {
    ConservacaoArmazenamentoMedicamento toEntity(ConservacaoArmazenamentoMedicamentoRequest request);
    ConservacaoArmazenamentoMedicamentoResponse toResponse(ConservacaoArmazenamentoMedicamento entity);
    void updateFromRequest(ConservacaoArmazenamentoMedicamentoRequest request, @MappingTarget ConservacaoArmazenamentoMedicamento entity);

    ConservacaoArmazenamentoMedicamento toEntity(com.upsaude.dto.embeddable.ConservacaoArmazenamentoMedicamentoDTO dto);
    com.upsaude.dto.embeddable.ConservacaoArmazenamentoMedicamentoDTO toDTO(ConservacaoArmazenamentoMedicamento entity);
    void updateFromDTO(com.upsaude.dto.embeddable.ConservacaoArmazenamentoMedicamentoDTO dto, @MappingTarget ConservacaoArmazenamentoMedicamento entity);
}
