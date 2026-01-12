package com.upsaude.mapper.farmacia;

import org.mapstruct.Mapper;
import com.upsaude.api.response.farmacia.UnidadeMedidaResponse;
import com.upsaude.entity.farmacia.UnidadeMedida;
import com.upsaude.mapper.config.MappingConfig;

@Mapper(config = MappingConfig.class)
public interface UnidadeMedidaMapper {
    UnidadeMedidaResponse toResponse(UnidadeMedida entity);
}
