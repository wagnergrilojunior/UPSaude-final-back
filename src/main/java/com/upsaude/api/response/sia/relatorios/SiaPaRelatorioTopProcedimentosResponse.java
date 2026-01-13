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
public class SiaPaRelatorioTopProcedimentosResponse {

    private String uf;
    private String competencia;
    private Integer limit;

    private List<ItemTopProcedimento> itens;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemTopProcedimento {
        private String procedimentoCodigo;
        private String procedimentoNome;
        private Long quantidadeProduzidaTotal;
        private BigDecimal valorAprovadoTotal;
        private Long estabelecimentosUnicos;
        private Long municipiosUnicos;
    }
}

