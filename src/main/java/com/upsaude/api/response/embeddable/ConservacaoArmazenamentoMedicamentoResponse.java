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
public class ConservacaoArmazenamentoMedicamentoResponse {
    private BigDecimal temperaturaConservacaoMin;
    private BigDecimal temperaturaConservacaoMax;
    private Boolean protegerLuz;
    private Boolean protegerUmidade;
    private String condicoesArmazenamento;
    private Integer validadeAposAberturaDias;
    private String instrucoesConservacao;
}
