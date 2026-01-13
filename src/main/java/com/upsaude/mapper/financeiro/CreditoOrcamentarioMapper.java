package com.upsaude.mapper.financeiro;

import com.upsaude.api.request.financeiro.CreditoOrcamentarioRequest;
import com.upsaude.api.response.financeiro.CreditoOrcamentarioResponse;
import com.upsaude.entity.financeiro.CreditoOrcamentario;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = { CompetenciaFinanceiraMapper.class })
public interface CreditoOrcamentarioMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "competencia", ignore = true)
    @Mapping(target = "idempotencyKey", ignore = true)
    CreditoOrcamentario fromRequest(CreditoOrcamentarioRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "competencia", ignore = true)
    @Mapping(target = "idempotencyKey", ignore = true)
    void updateFromRequest(CreditoOrcamentarioRequest request, @MappingTarget CreditoOrcamentario entity);

    CreditoOrcamentarioResponse toResponse(CreditoOrcamentario entity);
}

