package com.upsaude.mapper.financeiro;

import com.upsaude.api.request.financeiro.TituloPagarRequest;
import com.upsaude.api.response.financeiro.TituloPagarResponse;
import com.upsaude.api.response.financeiro.TituloPagarSimplificadoResponse;
import com.upsaude.entity.financeiro.TituloPagar;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(config = MappingConfig.class, uses = {
        ParteFinanceiraMapper.class,
        ContaContabilMapper.class,
        CentroCustoMapper.class,
        RecorrenciaFinanceiraMapper.class
})
public interface TituloPagarMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "fornecedor", ignore = true)
    @Mapping(target = "contaContabilDespesa", ignore = true)
    @Mapping(target = "centroCusto", ignore = true)
    @Mapping(target = "recorrenciaFinanceira", ignore = true)
    @Mapping(target = "pagamentos", ignore = true)
    @Mapping(target = "idempotencyKey", ignore = true)
    TituloPagar fromRequest(TituloPagarRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "fornecedor", ignore = true)
    @Mapping(target = "contaContabilDespesa", ignore = true)
    @Mapping(target = "centroCusto", ignore = true)
    @Mapping(target = "recorrenciaFinanceira", ignore = true)
    @Mapping(target = "pagamentos", ignore = true)
    @Mapping(target = "idempotencyKey", ignore = true)
    void updateFromRequest(TituloPagarRequest request, @MappingTarget TituloPagar entity);

    TituloPagarResponse toResponse(TituloPagar entity);

    @Named("toSimplifiedResponse")
    TituloPagarSimplificadoResponse toSimplifiedResponse(TituloPagar entity);
}

