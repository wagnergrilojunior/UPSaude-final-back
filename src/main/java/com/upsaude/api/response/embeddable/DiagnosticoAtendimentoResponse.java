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
public class DiagnosticoAtendimentoResponse {
    private String diagnostico;
    private String diagnosticosSecundarios;
    private String hipoteseDiagnostica;
    private String conduta;
    private String evolucao;
}
