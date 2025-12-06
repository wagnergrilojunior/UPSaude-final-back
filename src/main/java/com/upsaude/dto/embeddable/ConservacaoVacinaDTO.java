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
public class ConservacaoVacinaDTO {
    private BigDecimal temperaturaConservacaoMin;
    private BigDecimal temperaturaConservacaoMax;
    private String tipoConservacao;
    private Boolean protegerLuz;
    private Boolean agitarAntesUso;
    private Integer validadeAposAberturaHoras;
    private Integer validadeAposReconstituicaoHoras;
}
