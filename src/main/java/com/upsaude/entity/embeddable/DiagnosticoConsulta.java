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
public class DiagnosticoConsulta {

    public DiagnosticoConsulta() {
        this.diagnostico = "";
        this.diagnosticosSecundarios = "";
        this.hipoteseDiagnostica = "";
        this.diagnosticoDiferencial = "";
        this.conduta = "";
    }

    @Deprecated
    @Column(name = "diagnostico", columnDefinition = "TEXT")
    private String diagnostico;

    @Deprecated
    @Column(name = "diagnosticos_secundarios", columnDefinition = "TEXT")
    private String diagnosticosSecundarios;

    @Column(name = "hipotese_diagnostica", columnDefinition = "TEXT")
    private String hipoteseDiagnostica;

    @Column(name = "diagnostico_diferencial", columnDefinition = "TEXT")
    private String diagnosticoDiferencial;

    @Column(name = "conduta", columnDefinition = "TEXT")
    private String conduta;
}
