package com.upsaude.mapper.farmacia;

import org.mapstruct.Mapper;
import com.upsaude.api.response.farmacia.PrincipioAtivoResponse;
import com.upsaude.entity.farmacia.PrincipioAtivo;
import com.upsaude.mapper.config.MappingConfig;

@Mapper(config = MappingConfig.class)
public interface PrincipioAtivoMapper {
    PrincipioAtivoResponse toResponse(PrincipioAtivo entity);
}
