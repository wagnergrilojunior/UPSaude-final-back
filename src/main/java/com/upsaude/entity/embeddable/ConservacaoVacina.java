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
public class ConservacaoVacina {

    public ConservacaoVacina() {
        this.tipoConservacao = "";
        this.protegerLuz = false;
        this.agitarAntesUso = false;
    }

    @Column(name = "temperatura_conservacao_min", precision = 5, scale = 2)
    private BigDecimal temperaturaConservacaoMin;

    @Column(name = "temperatura_conservacao_max", precision = 5, scale = 2)
    private BigDecimal temperaturaConservacaoMax;

    @Column(name = "tipo_conservacao", length = 50)
    private String tipoConservacao;

    @Column(name = "proteger_luz", nullable = false)
    @Default
    private Boolean protegerLuz = false;

    @Column(name = "agitar_antes_uso", nullable = false)
    @Default
    private Boolean agitarAntesUso = false;

    @Column(name = "validade_apos_abertura_horas")
    private Integer validadeAposAberturaHoras;

    @Column(name = "validade_apos_reconstituicao_horas")
    private Integer validadeAposReconstituicaoHoras;
}
