package com.upsaude.api.response.embeddable;

import com.upsaude.enums.TipoReacaoAlergicaEnum;
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
public class ReacoesAlergiaResponse {
    private TipoReacaoAlergicaEnum tipoReacaoPrincipal;
    private String reacoesComuns;
    private String reacoesGraves;
    private String sintomas;
    private String tempoAposExposicao;
}
