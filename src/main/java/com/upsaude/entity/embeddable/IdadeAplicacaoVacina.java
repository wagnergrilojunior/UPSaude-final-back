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
public class IdadeAplicacaoVacina {

    public IdadeAplicacaoVacina() {

    }

    @Column(name = "idade_minima_dias")
    private Integer idadeMinimaDias;

    @Column(name = "idade_maxima_dias")
    private Integer idadeMaximaDias;

    @Column(name = "idade_minima_meses")
    private Integer idadeMinimaMeses;

    @Column(name = "idade_maxima_meses")
    private Integer idadeMaximaMeses;

    @Column(name = "idade_minima_anos")
    private Integer idadeMinimaAnos;

    @Column(name = "idade_maxima_anos")
    private Integer idadeMaximaAnos;
}
