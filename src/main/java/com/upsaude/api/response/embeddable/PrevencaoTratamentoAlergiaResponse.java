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
public class PrevencaoTratamentoAlergiaResponse {
    private String medidasPrevencao;
    private String tratamentoImediato;
    private String medicamentosEvitar;
    private String alimentosEvitar;
    private String substanciasEvitar;
    private Boolean epinefrinaNecessaria;
    private String antihistaminicoRecomendado;
}
