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
public class ReacoesAdversasVacina {

    public ReacoesAdversasVacina() {
        this.reacoesAdversasComuns = "";
        this.reacoesAdversasRaras = "";
        this.reacoesAdversasGraves = "";
    }

    @Column(name = "reacoes_adversas_comuns", columnDefinition = "TEXT")
    private String reacoesAdversasComuns;

    @Column(name = "reacoes_adversas_raras", columnDefinition = "TEXT")
    private String reacoesAdversasRaras;

    @Column(name = "reacoes_adversas_graves", columnDefinition = "TEXT")
    private String reacoesAdversasGraves;
}
