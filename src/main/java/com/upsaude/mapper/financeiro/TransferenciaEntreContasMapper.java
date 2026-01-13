package com.upsaude.mapper.financeiro;

import com.upsaude.api.request.financeiro.TransferenciaEntreContasRequest;
import com.upsaude.api.response.financeiro.TransferenciaEntreContasResponse;
import com.upsaude.api.response.financeiro.TransferenciaEntreContasSimplificadaResponse;
import com.upsaude.entity.financeiro.TransferenciaEntreContas;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(config = MappingConfig.class, uses = { ContaFinanceiraMapper.class })
public interface TransferenciaEntreContasMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "contaOrigem", ignore = true)
    @Mapping(target = "contaDestino", ignore = true)
    @Mapping(target = "idempotencyKey", ignore = true)
    TransferenciaEntreContas fromRequest(TransferenciaEntreContasRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "contaOrigem", ignore = true)
    @Mapping(target = "contaDestino", ignore = true)
    @Mapping(target = "idempotencyKey", ignore = true)
    void updateFromRequest(TransferenciaEntreContasRequest request, @MappingTarget TransferenciaEntreContas entity);

    TransferenciaEntreContasResponse toResponse(TransferenciaEntreContas entity);

    @Named("toSimplifiedResponse")
    TransferenciaEntreContasSimplificadaResponse toSimplifiedResponse(TransferenciaEntreContas entity);
}

