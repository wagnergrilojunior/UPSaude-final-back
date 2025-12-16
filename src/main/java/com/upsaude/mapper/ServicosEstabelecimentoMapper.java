package com.upsaude.mapper;

import com.upsaude.api.request.ServicosEstabelecimentoRequest;
import com.upsaude.api.response.ServicosEstabelecimentoResponse;
import com.upsaude.dto.ServicosEstabelecimentoDTO;
import com.upsaude.entity.ServicosEstabelecimento;
import com.upsaude.entity.Estabelecimentos;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = {EstabelecimentosMapper.class})
public interface ServicosEstabelecimentoMapper extends EntityMapper<ServicosEstabelecimento, ServicosEstabelecimentoDTO> {

    @Mapping(target = "active", ignore = true)
    ServicosEstabelecimento toEntity(ServicosEstabelecimentoDTO dto);

    ServicosEstabelecimentoDTO toDTO(ServicosEstabelecimento entity);

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

    ServicosEstabelecimentoResponse toResponse(ServicosEstabelecimento entity);
}
