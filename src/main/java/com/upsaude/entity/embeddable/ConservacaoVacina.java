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
 * Classe embeddable para informações de conservação e armazenamento de vacinas.
 *
 * @author UPSaúde
 */
@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConservacaoVacina {

    @Column(name = "temperatura_conservacao_min", precision = 5, scale = 2)
    private BigDecimal temperaturaConservacaoMin; // Temperatura mínima de conservação em °C

    @Column(name = "temperatura_conservacao_max", precision = 5, scale = 2)
    private BigDecimal temperaturaConservacaoMax; // Temperatura máxima de conservação em °C

    @Column(name = "tipo_conservacao", length = 50)
    private String tipoConservacao; // Ex: Refrigerada (2-8°C), Congelada (-15°C), Ambiente

    @Column(name = "proteger_luz", nullable = false)
    @Default
    private Boolean protegerLuz = false; // Se deve ser protegida da luz

    @Column(name = "agitar_antes_uso", nullable = false)
    @Default
    private Boolean agitarAntesUso = false; // Se deve ser agitada antes do uso

    @Column(name = "validade_apos_abertura_horas")
    private Integer validadeAposAberturaHoras; // Validade após abertura em horas

    @Column(name = "validade_apos_reconstituicao_horas")
    private Integer validadeAposReconstituicaoHoras; // Validade após reconstituição em horas
}

