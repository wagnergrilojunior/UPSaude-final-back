package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.DescricoesEquipamentoRequest;
import com.upsaude.api.response.embeddable.DescricoesEquipamentoResponse;
import com.upsaude.entity.embeddable.DescricoesEquipamento;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface DescricoesEquipamentoMapper {
    DescricoesEquipamento toEntity(DescricoesEquipamentoRequest request);
    DescricoesEquipamentoResponse toResponse(DescricoesEquipamento entity);
    void updateFromRequest(DescricoesEquipamentoRequest request, @MappingTarget DescricoesEquipamento entity);
}
