package com.upsaude.api.response.sia.financeiro;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SiaPaFinanceiroIntegracaoResponse {

    private UUID tenantId;
    private String uf;
    private String competencia;

    private BigDecimal siaValorAprovadoTotal;
    private BigDecimal faturamentoValorTotal;
    private BigDecimal deltaValor;
    private BigDecimal deltaValorPct;

    private Long siaQuantidadeTotal;
    private Long faturamentoQuantidadeTotal;
    private Long deltaQuantidade;

    private List<ItemConciliacaoProcedimento> itens;
    private List<ItemConciliacaoProcedimento> procedimentosNaoFaturados;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemConciliacaoProcedimento {
        private String procedimentoCodigo;
        private String procedimentoNome;

        private Long siaQuantidade;
        private BigDecimal siaValorAprovado;

        private Long faturamentoQuantidade;
        private BigDecimal faturamentoValor;

        private BigDecimal deltaValor;
        private BigDecimal deltaValorPct;
    }
}

