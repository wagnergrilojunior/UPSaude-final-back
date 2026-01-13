package com.upsaude.mapper.financeiro;

import com.upsaude.api.request.financeiro.LogFinanceiroRequest;
import com.upsaude.api.response.financeiro.LogFinanceiroResponse;
import com.upsaude.entity.financeiro.LogFinanceiro;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface LogFinanceiroMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    LogFinanceiro fromRequest(LogFinanceiroRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    void updateFromRequest(LogFinanceiroRequest request, @MappingTarget LogFinanceiro entity);

    @Mapping(target = "criadoEm", source = "createdAt")
    LogFinanceiroResponse toResponse(LogFinanceiro entity);
}

