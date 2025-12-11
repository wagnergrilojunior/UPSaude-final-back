package com.upsaude.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Embeddable
@Data
@Builder
@AllArgsConstructor
public class PrevencaoTratamentoAlergia {

    @Column(name = "medidas_prevencao", columnDefinition = "TEXT")
    private String medidasPrevencao;

    @Column(name = "tratamento_imediato", columnDefinition = "TEXT")
    private String tratamentoImediato;

    @Column(name = "medicamentos_evitar", columnDefinition = "TEXT")
    private String medicamentosEvitar;

    @Column(name = "alimentos_evitar", columnDefinition = "TEXT")
    private String alimentosEvitar;

    @Column(name = "substancias_evitar", columnDefinition = "TEXT")
    private String substanciasEvitar;

    @Column(name = "epinefrina_necessaria", nullable = false)
    private Boolean epinefrinaNecessaria;

    @Column(name = "antihistaminico_recomendado", length = 255)
    private String antihistaminicoRecomendado;

    public PrevencaoTratamentoAlergia() {
        this.epinefrinaNecessaria = false;
        this.medidasPrevencao = "";
        this.tratamentoImediato = "";
        this.medicamentosEvitar = "";
        this.alimentosEvitar = "";
        this.substanciasEvitar = "";
        this.antihistaminicoRecomendado = "";
    }
}
