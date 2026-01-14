package com.upsaude.mapper.estabelecimento;

import com.upsaude.api.request.estabelecimento.ContaBancariaEstabelecimentoRequest;
import com.upsaude.api.response.estabelecimento.ContaBancariaEstabelecimentoResponse;
import com.upsaude.entity.estabelecimento.ContaBancariaEstabelecimento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ContaBancariaEstabelecimentoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "estabelecimento", ignore = true)
    ContaBancariaEstabelecimento toEntity(ContaBancariaEstabelecimentoRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "estabelecimento", ignore = true)
    void updateEntityFromRequest(ContaBancariaEstabelecimentoRequest request,
            @MappingTarget ContaBancariaEstabelecimento entity);

    @Mapping(source = "createdAt", target = "criadoEm")
    @Mapping(source = "updatedAt", target = "atualizadoEm")
    @Mapping(source = "active", target = "ativo")
    ContaBancariaEstabelecimentoResponse toResponse(ContaBancariaEstabelecimento entity);

    List<ContaBancariaEstabelecimentoResponse> toResponseList(List<ContaBancariaEstabelecimento> entities);
}
