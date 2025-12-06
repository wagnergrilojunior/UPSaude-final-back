package com.upsaude.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Classe embeddable para informações de reações adversas da vacina.
 *
 * @author UPSaúde
 */
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
    private String reacoesAdversasComuns; // Reações adversas comuns

    @Column(name = "reacoes_adversas_raras", columnDefinition = "TEXT")
    private String reacoesAdversasRaras; // Reações adversas raras

    @Column(name = "reacoes_adversas_graves", columnDefinition = "TEXT")
    private String reacoesAdversasGraves; // Reações adversas graves
}

