package com.upsaude.mapper.financeiro;

import com.upsaude.api.request.financeiro.ParteFinanceiraRequest;
import com.upsaude.api.response.financeiro.ParteFinanceiraResponse;
import com.upsaude.api.response.financeiro.ParteFinanceiraSimplificadaResponse;
import com.upsaude.entity.financeiro.ParteFinanceira;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(config = MappingConfig.class)
public interface ParteFinanceiraMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    ParteFinanceira fromRequest(ParteFinanceiraRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    void updateFromRequest(ParteFinanceiraRequest request, @MappingTarget ParteFinanceira entity);

    ParteFinanceiraResponse toResponse(ParteFinanceira entity);

    @Named("toSimplifiedResponse")
    ParteFinanceiraSimplificadaResponse toSimplifiedResponse(ParteFinanceira entity);
}

