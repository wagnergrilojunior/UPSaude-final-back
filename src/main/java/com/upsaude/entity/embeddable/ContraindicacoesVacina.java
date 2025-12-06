package com.upsaude.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;

/**
 * Classe embeddable para informações de contraindicações e precauções da vacina.
 *
 * @author UPSaúde
 */
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
    private String contraindicacoes; // Contraindicações gerais

    @Column(name = "precaucoes", columnDefinition = "TEXT")
    private String precaucoes; // Precauções especiais

    @Column(name = "gestante_pode", nullable = false)
    @Default
    private Boolean gestantePode = false; // Se gestante pode receber

    @Column(name = "lactante_pode", nullable = false)
    @Default
    private Boolean lactantePode = false; // Se lactante pode receber

    @Column(name = "imunocomprometido_pode", nullable = false)
    @Default
    private Boolean imunocomprometidoPode = false; // Se imunocomprometido pode receber
}

