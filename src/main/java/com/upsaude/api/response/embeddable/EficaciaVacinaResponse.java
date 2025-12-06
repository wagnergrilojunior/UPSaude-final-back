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
public class EficaciaVacinaResponse {
    private BigDecimal eficaciaPercentual;
    private Integer protecaoInicioDias;
    private Integer protecaoDuracaoAnos;
    private String doencasProtege;
}
