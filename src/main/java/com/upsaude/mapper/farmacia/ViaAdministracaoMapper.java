package com.upsaude.mapper.farmacia;

import org.mapstruct.Mapper;
import com.upsaude.api.response.farmacia.ViaAdministracaoResponse;
import com.upsaude.entity.vacinacao.ViaAdministracao;
import com.upsaude.mapper.config.MappingConfig;

@Mapper(config = MappingConfig.class)
public interface ViaAdministracaoMapper {
    ViaAdministracaoResponse toResponse(ViaAdministracao entity);
}
