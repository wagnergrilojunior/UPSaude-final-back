package com.upsaude.mapper.estabelecimento;

import com.upsaude.api.request.estabelecimento.ConfiguracaoEstabelecimentoRequest;
import com.upsaude.api.response.estabelecimento.ConfiguracaoEstabelecimentoResponse;
import com.upsaude.dto.ConfiguracaoEstabelecimentoDTO;
import com.upsaude.entity.estabelecimento.ConfiguracaoEstabelecimento;
import com.upsaude.entity.estabelecimento.Estabelecimentos;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = {EstabelecimentosMapper.class})
public interface ConfiguracaoEstabelecimentoMapper extends EntityMapper<ConfiguracaoEstabelecimento, ConfiguracaoEstabelecimentoDTO> {

    @Mapping(target = "active", ignore = true)
    ConfiguracaoEstabelecimento toEntity(ConfiguracaoEstabelecimentoDTO dto);

    ConfiguracaoEstabelecimentoDTO toDTO(ConfiguracaoEstabelecimento entity);

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
    void updateFromRequest(ConfiguracaoEstabelecimentoRequest request, @MappingTarget ConfiguracaoEstabelecimento entity);

    ConfiguracaoEstabelecimentoResponse toResponse(ConfiguracaoEstabelecimento entity);
}
