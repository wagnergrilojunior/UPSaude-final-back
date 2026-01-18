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
public class SiaPaSazonalidadeResponse {

    private String uf;
    private String competenciaInicio;
    private String competenciaFim;

    private List<ItemMes> meses;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemMes {
        private String mes; 
        private BigDecimal valorAprovadoMedio;
        private BigDecimal quantidadeProduzidaMedia;
    }
}

