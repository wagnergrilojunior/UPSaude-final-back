package com.upsaude.mapper.financeiro;

import com.upsaude.api.request.financeiro.PlanoContasRequest;
import com.upsaude.api.response.financeiro.PlanoContasResponse;
import com.upsaude.api.response.financeiro.PlanoContasSimplificadoResponse;
import com.upsaude.entity.financeiro.PlanoContas;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(config = MappingConfig.class)
public interface PlanoContasMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", source = "ativo")
    PlanoContas fromRequest(PlanoContasRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", source = "ativo")
    void updateFromRequest(PlanoContasRequest request, @MappingTarget PlanoContas entity);

    @Mapping(target = "ativo", source = "active")
    PlanoContasResponse toResponse(PlanoContas entity);

    @Named("toSimplifiedResponse")
    PlanoContasSimplificadoResponse toSimplifiedResponse(PlanoContas entity);
}

