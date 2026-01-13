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
public class SiaPaRankingResponse {

    private String uf;
    private String competencia;
    private Integer limit;

    private List<ItemRanking> itens;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemRanking {
        private Integer posicao;
        private String chave; // ex: CNES, procedimento, município
        private String descricao; // ex: nome do estabelecimento/procedimento/cidade
        private BigDecimal valorAprovadoTotal;
        private Long quantidadeProduzidaTotal;
    }
}

