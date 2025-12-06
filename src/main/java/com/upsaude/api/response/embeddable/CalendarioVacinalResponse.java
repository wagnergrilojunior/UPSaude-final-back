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
public class CalendarioVacinalResponse {
    private Boolean calendarioBasico;
    private Boolean calendarioCampanha;
    private String faixaEtariaCalendario;
    private String situacaoEpidemiologica;
    private Boolean obrigatoria;
}
