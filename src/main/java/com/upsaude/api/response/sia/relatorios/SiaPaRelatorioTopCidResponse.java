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
public class SiaPaRelatorioTopCidResponse {

    private String uf;
    private String competencia;
    private Integer limit;

    private List<ItemTopCid> itens;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemTopCid {
        private String cidPrincipalCodigo;
        private String cidDescricao;
        private Long quantidadeProduzidaTotal;
        private BigDecimal valorAprovadoTotal;
        private String topProcedimentoCodigo;
        private Long topProcedimentoTotal;
        private String topMunicipioUfmunCodigo;
        private Long topMunicipioTotal;
    }
}

