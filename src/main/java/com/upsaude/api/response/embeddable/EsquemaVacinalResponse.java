package com.upsaude.api.response.embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EsquemaVacinalResponse {
    private Integer numeroDoses;
    private Integer intervaloDosesDias;
    private Boolean doseReforco;
    private Integer intervaloReforcoAnos;
    private BigDecimal doseMl;
    private String localAplicacaoPadrao;
}
