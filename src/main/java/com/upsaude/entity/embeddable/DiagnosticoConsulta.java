package com.upsaude.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Classe embeddable para informações de diagnóstico da consulta.
 *
 * @author UPSaúde
 */
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

    @Column(name = "diagnostico", columnDefinition = "TEXT")
    private String diagnostico; // Diagnóstico principal

    @Column(name = "diagnosticos_secundarios", columnDefinition = "TEXT")
    private String diagnosticosSecundarios; // Diagnósticos secundários

    @Column(name = "hipotese_diagnostica", columnDefinition = "TEXT")
    private String hipoteseDiagnostica; // Hipótese diagnóstica

    @Column(name = "diagnostico_diferencial", columnDefinition = "TEXT")
    private String diagnosticoDiferencial; // Diagnóstico diferencial

    @Column(name = "conduta", columnDefinition = "TEXT")
    private String conduta; // Conduta adotada
}

