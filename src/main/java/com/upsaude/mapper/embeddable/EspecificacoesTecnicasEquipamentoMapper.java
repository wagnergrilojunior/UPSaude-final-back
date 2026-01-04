package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.EspecificacoesTecnicasEquipamentoRequest;
import com.upsaude.api.response.embeddable.EspecificacoesTecnicasEquipamentoResponse;
import com.upsaude.entity.embeddable.EspecificacoesTecnicasEquipamento;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface EspecificacoesTecnicasEquipamentoMapper {
    EspecificacoesTecnicasEquipamento toEntity(EspecificacoesTecnicasEquipamentoRequest request);
    EspecificacoesTecnicasEquipamentoResponse toResponse(EspecificacoesTecnicasEquipamento entity);
    void updateFromRequest(EspecificacoesTecnicasEquipamentoRequest request, @MappingTarget EspecificacoesTecnicasEquipamento entity);
}

