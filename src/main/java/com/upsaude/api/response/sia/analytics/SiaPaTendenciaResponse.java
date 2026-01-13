package com.upsaude.api.response.sia.analytics;

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
public class SiaPaTendenciaResponse {

    private String uf;
    private String competenciaInicio;
    private String competenciaFim;

    private List<PontoTendencia> pontos;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PontoTendencia {
        private String competencia;
        private Long quantidadeProduzidaTotal;
        private BigDecimal valorProduzidoTotal;
        private BigDecimal valorAprovadoTotal;
        private BigDecimal valorAprovadoPrev;
        private BigDecimal deltaValorAprovado;
        private BigDecimal crescimentoValorAprovadoPct;
    }
}

