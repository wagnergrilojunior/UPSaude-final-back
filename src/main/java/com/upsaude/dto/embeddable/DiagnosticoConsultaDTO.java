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
public class DiagnosticoConsultaDTO {
    private String diagnostico;
    private String diagnosticosSecundarios;
    private String hipoteseDiagnostica;
    private String diagnosticoDiferencial;
    private String conduta;
}
