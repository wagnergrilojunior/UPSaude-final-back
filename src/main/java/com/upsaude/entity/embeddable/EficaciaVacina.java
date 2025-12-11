package com.upsaude.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Embeddable
@Data
@Builder
@AllArgsConstructor
public class EficaciaVacina {

    public EficaciaVacina() {
        this.doencasProtege = "";
    }

    @Column(name = "eficacia_percentual", precision = 5, scale = 2)
    private BigDecimal eficaciaPercentual;

    @Column(name = "protecao_inicio_dias")
    private Integer protecaoInicioDias;

    @Column(name = "protecao_duracao_anos")
    private Integer protecaoDuracaoAnos;

    @Column(name = "doencas_protege", columnDefinition = "TEXT")
    private String doencasProtege;
}
