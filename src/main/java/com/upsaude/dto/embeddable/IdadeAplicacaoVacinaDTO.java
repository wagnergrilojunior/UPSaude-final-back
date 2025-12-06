package com.upsaude.dto.embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IdadeAplicacaoVacinaDTO {
    private Integer idadeMinimaDias;
    private Integer idadeMaximaDias;
    private Integer idadeMinimaMeses;
    private Integer idadeMaximaMeses;
    private Integer idadeMinimaAnos;
    private Integer idadeMaximaAnos;
}
