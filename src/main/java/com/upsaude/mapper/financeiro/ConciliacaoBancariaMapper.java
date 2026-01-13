package com.upsaude.mapper.financeiro;

import com.upsaude.api.request.financeiro.ConciliacaoBancariaRequest;
import com.upsaude.api.response.financeiro.ConciliacaoBancariaResponse;
import com.upsaude.api.response.financeiro.ConciliacaoBancariaSimplificadaResponse;
import com.upsaude.entity.financeiro.ConciliacaoBancaria;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(config = MappingConfig.class, uses = { ContaFinanceiraMapper.class, ConciliacaoItemMapper.class })
public interface ConciliacaoBancariaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "contaFinanceira", ignore = true)
    @Mapping(target = "itens", ignore = true)
    ConciliacaoBancaria fromRequest(ConciliacaoBancariaRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "contaFinanceira", ignore = true)
    @Mapping(target = "itens", ignore = true)
    void updateFromRequest(ConciliacaoBancariaRequest request, @MappingTarget ConciliacaoBancaria entity);

    ConciliacaoBancariaResponse toResponse(ConciliacaoBancaria entity);

    @Named("toSimplifiedResponse")
    ConciliacaoBancariaSimplificadaResponse toSimplifiedResponse(ConciliacaoBancaria entity);
}

