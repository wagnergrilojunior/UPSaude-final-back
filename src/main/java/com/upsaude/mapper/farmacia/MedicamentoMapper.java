package com.upsaude.mapper.farmacia;

import org.mapstruct.Mapper;
import com.upsaude.api.response.farmacia.MedicamentoResponse;
import com.upsaude.entity.farmacia.Medicamento;
import com.upsaude.mapper.config.MappingConfig;

@Mapper(config = MappingConfig.class, uses = { PrincipioAtivoMapper.class })
public interface MedicamentoMapper {
    MedicamentoResponse toResponse(Medicamento entity);
}
