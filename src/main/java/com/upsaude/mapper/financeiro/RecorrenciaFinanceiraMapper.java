package com.upsaude.mapper.financeiro;

import com.upsaude.api.request.financeiro.RecorrenciaFinanceiraRequest;
import com.upsaude.api.response.financeiro.RecorrenciaFinanceiraResponse;
import com.upsaude.api.response.financeiro.RecorrenciaFinanceiraSimplificadaResponse;
import com.upsaude.entity.financeiro.RecorrenciaFinanceira;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(config = MappingConfig.class)
public interface RecorrenciaFinanceiraMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", source = "ativo")
    RecorrenciaFinanceira fromRequest(RecorrenciaFinanceiraRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", source = "ativo")
    void updateFromRequest(RecorrenciaFinanceiraRequest request, @MappingTarget RecorrenciaFinanceira entity);

    @Mapping(target = "ativo", source = "active")
    RecorrenciaFinanceiraResponse toResponse(RecorrenciaFinanceira entity);

    @Named("toSimplifiedResponse")
    RecorrenciaFinanceiraSimplificadaResponse toSimplifiedResponse(RecorrenciaFinanceira entity);
}

