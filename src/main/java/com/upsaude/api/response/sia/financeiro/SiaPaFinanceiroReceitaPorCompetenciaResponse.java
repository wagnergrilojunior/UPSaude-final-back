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
public class SiaPaFinanceiroReceitaPorCompetenciaResponse {

    private UUID tenantId;
    private String uf;
    private String competenciaInicio;
    private String competenciaFim;

    private List<Item> itens;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Item {
        private String competencia;
        private BigDecimal faturamentoValorTotal;
        private BigDecimal siaValorAprovadoTotal;
        private BigDecimal deltaValor;
    }
}

