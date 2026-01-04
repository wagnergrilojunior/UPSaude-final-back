package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.ManutencaoCalibracaoEquipamentoRequest;
import com.upsaude.api.response.embeddable.ManutencaoCalibracaoEquipamentoResponse;
import com.upsaude.entity.embeddable.ManutencaoCalibracaoEquipamento;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface ManutencaoCalibracaoEquipamentoMapper {
    ManutencaoCalibracaoEquipamento toEntity(ManutencaoCalibracaoEquipamentoRequest request);
    ManutencaoCalibracaoEquipamentoResponse toResponse(ManutencaoCalibracaoEquipamento entity);
    void updateFromRequest(ManutencaoCalibracaoEquipamentoRequest request, @MappingTarget ManutencaoCalibracaoEquipamento entity);
}

