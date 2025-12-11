package com.upsaude.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;

import java.math.BigDecimal;

@Embeddable
@Data
@Builder
@AllArgsConstructor
public class EsquemaVacinal {

    public EsquemaVacinal() {
        this.numeroDoses = 1;
        this.doseReforco = false;
        this.localAplicacaoPadrao = "";
    }

    @Column(name = "numero_doses", nullable = false)
    @Default
    private Integer numeroDoses = 1;

    @Column(name = "intervalo_doses_dias")
    private Integer intervaloDosesDias;

    @Column(name = "dose_reforco", nullable = false)
    @Default
    private Boolean doseReforco = false;

    @Column(name = "intervalo_reforco_anos")
    private Integer intervaloReforcoAnos;

    @Column(name = "dose_ml", precision = 10, scale = 2)
    private BigDecimal doseMl;

    @Column(name = "local_aplicacao_padrao", length = 100)
    private String localAplicacaoPadrao;
}
