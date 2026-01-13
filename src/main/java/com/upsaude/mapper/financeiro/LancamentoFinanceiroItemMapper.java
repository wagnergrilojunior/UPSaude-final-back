package com.upsaude.mapper.financeiro;

import com.upsaude.api.request.financeiro.LancamentoFinanceiroItemRequest;
import com.upsaude.api.response.financeiro.LancamentoFinanceiroItemResponse;
import com.upsaude.api.response.financeiro.LancamentoFinanceiroSimplificadoResponse;
import com.upsaude.entity.financeiro.LancamentoFinanceiro;
import com.upsaude.entity.financeiro.LancamentoFinanceiroItem;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(config = MappingConfig.class, uses = { ContaContabilMapper.class, CentroCustoMapper.class })
public interface LancamentoFinanceiroItemMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "lancamento", ignore = true)
    @Mapping(target = "contaContabil", ignore = true)
    @Mapping(target = "centroCusto", ignore = true)
    LancamentoFinanceiroItem fromRequest(LancamentoFinanceiroItemRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "lancamento", ignore = true)
    @Mapping(target = "contaContabil", ignore = true)
    @Mapping(target = "centroCusto", ignore = true)
    void updateFromRequest(LancamentoFinanceiroItemRequest request, @MappingTarget LancamentoFinanceiroItem entity);

    @Mapping(target = "lancamento", source = "lancamento", qualifiedByName = "mapLancamentoSimplificado")
    @Mapping(target = "contaContabil", source = "contaContabil", qualifiedByName = "toSimplifiedResponse")
    @Mapping(target = "centroCusto", source = "centroCusto", qualifiedByName = "toSimplifiedResponse")
    LancamentoFinanceiroItemResponse toResponse(LancamentoFinanceiroItem entity);

    @Named("mapLancamentoSimplificado")
    default LancamentoFinanceiroSimplificadoResponse mapLancamentoSimplificado(LancamentoFinanceiro lancamento) {
        if (lancamento == null) return null;
        try {
            return LancamentoFinanceiroSimplificadoResponse.builder()
                    .id(lancamento.getId())
                    .status(lancamento.getStatus())
                    .dataEvento(lancamento.getDataEvento())
                    .descricao(lancamento.getDescricao())
                    .build();
        } catch (jakarta.persistence.EntityNotFoundException e) {
            return null;
        }
    }
}

