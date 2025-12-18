package com.upsaude.mapper.estabelecimento;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.upsaude.api.request.estabelecimento.InfraestruturaEstabelecimentoRequest;
import com.upsaude.api.response.estabelecimento.InfraestruturaEstabelecimentoResponse;
import com.upsaude.dto.estabelecimento.InfraestruturaEstabelecimentoDTO;
import com.upsaude.entity.estabelecimento.InfraestruturaEstabelecimento;
import com.upsaude.mapper.EntityMapper;
import com.upsaude.mapper.config.MappingConfig;

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
