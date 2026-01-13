package com.upsaude.api.response.sia.relatorios;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SiaPaRelatorioProducaoResponse {

    private String uf;
    private String competenciaInicio;
    private String competenciaFim;

    private List<ItemProducaoMensal> itens;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemProducaoMensal {
        private String competencia;
        private Long quantidadeProduzidaTotal;
        private BigDecimal valorProduzidoTotal;
        private BigDecimal valorAprovadoTotal;
        private BigDecimal valorAprovadoPrev;
        private BigDecimal deltaValorAprovado;
        private BigDecimal crescimentoValorAprovadoPct;
    }
}

