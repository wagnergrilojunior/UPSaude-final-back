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
public class DiagnosticoAtendimento {

    public DiagnosticoAtendimento() {
        this.diagnostico = "";
        this.diagnosticosSecundarios = "";
        this.hipoteseDiagnostica = "";
        this.conduta = "";
        this.evolucao = "";
    }

    @Column(name = "diagnostico", columnDefinition = "TEXT")
    private String diagnostico;

    @Column(name = "diagnosticos_secundarios", columnDefinition = "TEXT")
    private String diagnosticosSecundarios;

    @Column(name = "hipotese_diagnostica", columnDefinition = "TEXT")
    private String hipoteseDiagnostica;

    @Column(name = "conduta", columnDefinition = "TEXT")
    private String conduta;

    @Column(name = "evolucao", columnDefinition = "TEXT")
    private String evolucao;
}
