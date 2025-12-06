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
public class ConservacaoArmazenamentoMedicamentoDTO {
    private BigDecimal temperaturaConservacaoMin;
    private BigDecimal temperaturaConservacaoMax;
    private Boolean protegerLuz;
    private Boolean protegerUmidade;
    private String condicoesArmazenamento;
    private Integer validadeAposAberturaDias;
    private String instrucoesConservacao;
}
