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
public class EficaciaVacinaDTO {
    private BigDecimal eficaciaPercentual;
    private Integer protecaoInicioDias;
    private Integer protecaoDuracaoAnos;
    private String doencasProtege;
}
