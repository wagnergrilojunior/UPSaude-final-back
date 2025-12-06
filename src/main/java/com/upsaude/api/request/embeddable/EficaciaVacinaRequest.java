package com.upsaude.api.request.embeddable;

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
public class EficaciaVacinaRequest {
    private BigDecimal eficaciaPercentual;
    private Integer protecaoInicioDias;
    private Integer protecaoDuracaoAnos;
    private String doencasProtege;
}
