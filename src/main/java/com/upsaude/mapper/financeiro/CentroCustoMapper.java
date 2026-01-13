package com.upsaude.mapper.financeiro;

import com.upsaude.api.request.financeiro.CentroCustoRequest;
import com.upsaude.api.response.financeiro.CentroCustoResponse;
import com.upsaude.api.response.financeiro.CentroCustoSimplificadoResponse;
import com.upsaude.entity.financeiro.CentroCusto;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(config = MappingConfig.class)
public interface CentroCustoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", source = "ativo")
    @Mapping(target = "pai", ignore = true)
    CentroCusto fromRequest(CentroCustoRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", source = "ativo")
    @Mapping(target = "pai", ignore = true)
    void updateFromRequest(CentroCustoRequest request, @MappingTarget CentroCusto entity);

    @Mapping(target = "ativo", source = "active")
    CentroCustoResponse toResponse(CentroCusto entity);

    @Named("toSimplifiedResponse")
    CentroCustoSimplificadoResponse toSimplifiedResponse(CentroCusto entity);
}

