package com.upsaude.api.response.sia.analytics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SiaPaComparacaoResponse {

    private String uf;

    private String competenciaBase;
    private String competenciaComparacao;

    private BigDecimal valorAprovadoBase;
    private BigDecimal valorAprovadoComparacao;
    private BigDecimal deltaValorAprovado;
    private BigDecimal deltaValorAprovadoPct;

    private Long quantidadeProduzidaBase;
    private Long quantidadeProduzidaComparacao;
    private Long deltaQuantidadeProduzida;
    private BigDecimal deltaQuantidadeProduzidaPct;
}

