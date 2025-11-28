package com.upsaude.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe embeddable para informações de idade mínima e máxima para aplicação da vacina.
 *
 * @author UPSaúde
 */
@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IdadeAplicacaoVacina {

    @Column(name = "idade_minima_dias")
    private Integer idadeMinimaDias; // Idade mínima para aplicação em dias

    @Column(name = "idade_maxima_dias")
    private Integer idadeMaximaDias; // Idade máxima para aplicação em dias (se houver)

    @Column(name = "idade_minima_meses")
    private Integer idadeMinimaMeses; // Idade mínima em meses (alternativa)

    @Column(name = "idade_maxima_meses")
    private Integer idadeMaximaMeses; // Idade máxima em meses (alternativa)

    @Column(name = "idade_minima_anos")
    private Integer idadeMinimaAnos; // Idade mínima em anos (alternativa)

    @Column(name = "idade_maxima_anos")
    private Integer idadeMaximaAnos; // Idade máxima em anos (alternativa)
}

