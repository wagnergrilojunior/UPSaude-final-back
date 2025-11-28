package com.upsaude.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Classe embeddable para informações de esquema vacinal e administração.
 *
 * @author UPSaúde
 */
@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EsquemaVacinal {

    @Column(name = "numero_doses", nullable = false)
    @Default
    private Integer numeroDoses = 1; // Número total de doses do esquema vacinal

    @Column(name = "intervalo_doses_dias")
    private Integer intervaloDosesDias; // Intervalo mínimo entre doses em dias

    @Column(name = "dose_reforco", nullable = false)
    @Default
    private Boolean doseReforco = false; // Se requer dose de reforço

    @Column(name = "intervalo_reforco_anos")
    private Integer intervaloReforcoAnos; // Intervalo para dose de reforço em anos

    @Column(name = "dose_ml", precision = 10, scale = 2)
    private BigDecimal doseMl; // Volume da dose em mililitros

    @Column(name = "local_aplicacao_padrao", length = 100)
    private String localAplicacaoPadrao; // Ex: Braço direito, Coxa, Glúteo
}

