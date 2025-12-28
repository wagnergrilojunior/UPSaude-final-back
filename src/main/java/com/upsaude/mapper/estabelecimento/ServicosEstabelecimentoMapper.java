package com.upsaude.mapper.estabelecimento;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.upsaude.api.request.estabelecimento.ServicosEstabelecimentoRequest;
import com.upsaude.api.response.estabelecimento.ServicosEstabelecimentoResponse;
import com.upsaude.entity.estabelecimento.ServicosEstabelecimento;
import com.upsaude.mapper.config.MappingConfig;

@Mapper(config = MappingConfig.class)
public interface ServicosEstabelecimentoMapper  {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "estabelecimento", ignore = true)
    ServicosEstabelecimento fromRequest(ServicosEstabelecimentoRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "estabelecimento", ignore = true)
    void updateFromRequest(ServicosEstabelecimentoRequest request, @MappingTarget ServicosEstabelecimento entity);

    @Mapping(target = "estabelecimento", ignore = true)
    ServicosEstabelecimentoResponse toResponse(ServicosEstabelecimento entity);
}
