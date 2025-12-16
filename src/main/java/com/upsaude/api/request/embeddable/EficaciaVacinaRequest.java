package com.upsaude.api.request.embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de eficacia vacina")
public class EficaciaVacinaRequest {
    private BigDecimal eficaciaPercentual;
    private Integer protecaoInicioDias;
    private Integer protecaoDuracaoAnos;
    private String doencasProtege;
}
