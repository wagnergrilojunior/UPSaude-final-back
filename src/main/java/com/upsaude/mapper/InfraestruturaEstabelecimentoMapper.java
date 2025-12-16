package com.upsaude.mapper;

import com.upsaude.api.request.InfraestruturaEstabelecimentoRequest;
import com.upsaude.api.response.InfraestruturaEstabelecimentoResponse;
import com.upsaude.dto.InfraestruturaEstabelecimentoDTO;
import com.upsaude.entity.InfraestruturaEstabelecimento;
import com.upsaude.entity.Estabelecimentos;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = {EstabelecimentosMapper.class})
public interface InfraestruturaEstabelecimentoMapper extends EntityMapper<InfraestruturaEstabelecimento, InfraestruturaEstabelecimentoDTO> {

    @Mapping(target = "active", ignore = true)
    InfraestruturaEstabelecimento toEntity(InfraestruturaEstabelecimentoDTO dto);

    InfraestruturaEstabelecimentoDTO toDTO(InfraestruturaEstabelecimento entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "estabelecimento", ignore = true)
    InfraestruturaEstabelecimento fromRequest(InfraestruturaEstabelecimentoRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "estabelecimento", ignore = true)
    void updateFromRequest(InfraestruturaEstabelecimentoRequest request, @MappingTarget InfraestruturaEstabelecimento entity);

    InfraestruturaEstabelecimentoResponse toResponse(InfraestruturaEstabelecimento entity);
}
