package com.upsaude.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;

@Embeddable
@Data
@Builder
@AllArgsConstructor
public class ContraindicacoesVacina {

    public ContraindicacoesVacina() {
        this.contraindicacoes = "";
        this.precaucoes = "";
        this.gestantePode = false;
        this.lactantePode = false;
        this.imunocomprometidoPode = false;
    }

    @Column(name = "contraindicacoes", columnDefinition = "TEXT")
    private String contraindicacoes;

    @Column(name = "precaucoes", columnDefinition = "TEXT")
    private String precaucoes;

    @Column(name = "gestante_pode", nullable = false)
    @Default
    private Boolean gestantePode = false;

    @Column(name = "lactante_pode", nullable = false)
    @Default
    private Boolean lactantePode = false;

    @Column(name = "imunocomprometido_pode", nullable = false)
    @Default
    private Boolean imunocomprometidoPode = false;
}
