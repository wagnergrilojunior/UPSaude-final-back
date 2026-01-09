package com.upsaude.mapper.estabelecimento;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.upsaude.api.request.estabelecimento.ConfiguracaoEstabelecimentoRequest;
import com.upsaude.api.response.estabelecimento.ConfiguracaoEstabelecimentoResponse;
import com.upsaude.entity.estabelecimento.ConfiguracaoEstabelecimento;
import com.upsaude.mapper.config.MappingConfig;

@Mapper(config = MappingConfig.class, uses = { EstabelecimentosMapper.class })
public interface ConfiguracaoEstabelecimentoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "estabelecimento", ignore = true)
    ConfiguracaoEstabelecimento fromRequest(ConfiguracaoEstabelecimentoRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "estabelecimento", ignore = true)
    void updateFromRequest(ConfiguracaoEstabelecimentoRequest request,
            @MappingTarget ConfiguracaoEstabelecimento entity);

    @Mapping(target = "estabelecimento.responsaveis.responsavelTecnico.sigtapOcupacao", ignore = true)
    @Mapping(target = "estabelecimento.responsaveis.responsavelAdministrativo.sigtapOcupacao", ignore = true)
    ConfiguracaoEstabelecimentoResponse toResponse(ConfiguracaoEstabelecimento entity);
}
