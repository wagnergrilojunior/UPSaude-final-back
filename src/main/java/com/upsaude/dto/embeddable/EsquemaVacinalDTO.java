package com.upsaude.dto.embeddable;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EsquemaVacinalDTO {
    private Integer numeroDoses;
    private Integer intervaloDosesDias;
    private Boolean doseReforco;
    private Integer intervaloReforcoAnos;
    private BigDecimal doseMl;
    private String localAplicacaoPadrao;
}
