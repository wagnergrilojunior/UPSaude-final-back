package com.upsaude.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe embeddable para contraindicações e precauções do medicamento.
 *
 * @author UPSaúde
 */
@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContraindicacoesPrecaucoesMedicamento {

    @Column(name = "contraindicacoes", columnDefinition = "TEXT")
    private String contraindicacoes; // Contraindicações gerais

    @Column(name = "precaucoes", columnDefinition = "TEXT")
    private String precaucoes; // Precauções de uso

    @Column(name = "gestante_pode", nullable = false)
    @Builder.Default
    private Boolean gestantePode = false; // Se gestante pode usar

    @Column(name = "lactante_pode", nullable = false)
    @Builder.Default
    private Boolean lactantePode = false; // Se lactante pode usar

    @Column(name = "crianca_pode", nullable = false)
    @Builder.Default
    private Boolean criancaPode = true; // Se criança pode usar

    @Column(name = "idoso_pode", nullable = false)
    @Builder.Default
    private Boolean idosoPode = true; // Se idoso pode usar

    @Column(name = "interacoes_medicamentosas", columnDefinition = "TEXT")
    private String interacoesMedicamentosas; // Interações medicamentosas conhecidas

    @Column(name = "efeitos_colaterais", columnDefinition = "TEXT")
    private String efeitosColaterais; // Efeitos colaterais comuns
}

