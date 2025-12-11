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
public class ContraindicacoesPrecaucoesMedicamento {

    public ContraindicacoesPrecaucoesMedicamento() {
        this.contraindicacoes = "";
        this.precaucoes = "";
        this.gestantePode = false;
        this.lactantePode = false;
        this.criancaPode = true;
        this.idosoPode = true;
        this.interacoesMedicamentosas = "";
        this.efeitosColaterais = "";
    }

    @Column(name = "contraindicacoes", columnDefinition = "TEXT")
    private String contraindicacoes;

    @Column(name = "precaucoes", columnDefinition = "TEXT")
    private String precaucoes;

    @Column(name = "gestante_pode", nullable = false)
    @Builder.Default
    private Boolean gestantePode = false;

    @Column(name = "lactante_pode", nullable = false)
    @Builder.Default
    private Boolean lactantePode = false;

    @Column(name = "crianca_pode", nullable = false)
    @Builder.Default
    private Boolean criancaPode = true;

    @Column(name = "idoso_pode", nullable = false)
    @Builder.Default
    private Boolean idosoPode = true;

    @Column(name = "interacoes_medicamentosas", columnDefinition = "TEXT")
    private String interacoesMedicamentosas;

    @Column(name = "efeitos_colaterais", columnDefinition = "TEXT")
    private String efeitosColaterais;
}
