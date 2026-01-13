package com.upsaude.mapper.financeiro;

import com.upsaude.api.request.financeiro.ContaFinanceiraRequest;
import com.upsaude.api.response.financeiro.ContaFinanceiraResponse;
import com.upsaude.api.response.financeiro.ContaFinanceiraSimplificadaResponse;
import com.upsaude.entity.financeiro.ContaFinanceira;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(config = MappingConfig.class)
public interface ContaFinanceiraMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", source = "ativo")
    ContaFinanceira fromRequest(ContaFinanceiraRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", source = "ativo")
    void updateFromRequest(ContaFinanceiraRequest request, @MappingTarget ContaFinanceira entity);

    @Mapping(target = "ativo", source = "active")
    ContaFinanceiraResponse toResponse(ContaFinanceira entity);

    @Named("toSimplifiedResponse")
    ContaFinanceiraSimplificadaResponse toSimplifiedResponse(ContaFinanceira entity);
}

