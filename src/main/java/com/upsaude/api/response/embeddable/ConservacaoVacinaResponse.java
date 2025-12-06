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
public class ConservacaoVacinaResponse {
    private BigDecimal temperaturaConservacaoMin;
    private BigDecimal temperaturaConservacaoMax;
    private String tipoConservacao;
    private Boolean protegerLuz;
    private Boolean agitarAntesUso;
    private Integer validadeAposAberturaHoras;
    private Integer validadeAposReconstituicaoHoras;
}
