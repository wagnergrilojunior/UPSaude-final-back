package com.upsaude.api.response.embeddable;

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
public class ContraindicacoesPrecaucoesMedicamentoResponse {
    private String contraindicacoes;
    private String precaucoes;
    private Boolean gestantePode;
    private Boolean lactantePode;
    private Boolean criancaPode;
    private Boolean idosoPode;
    private String interacoesMedicamentosas;
    private String efeitosColaterais;
}
