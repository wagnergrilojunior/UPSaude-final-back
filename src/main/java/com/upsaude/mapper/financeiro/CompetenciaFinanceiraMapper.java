package com.upsaude.mapper.financeiro;

import com.upsaude.api.request.financeiro.CompetenciaFinanceiraRequest;
import com.upsaude.api.response.financeiro.CompetenciaFinanceiraResponse;
import com.upsaude.entity.financeiro.CompetenciaFinanceira;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface CompetenciaFinanceiraMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    CompetenciaFinanceira fromRequest(CompetenciaFinanceiraRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    void updateFromRequest(CompetenciaFinanceiraRequest request, @MappingTarget CompetenciaFinanceira entity);

    CompetenciaFinanceiraResponse toResponse(CompetenciaFinanceira entity);
}

