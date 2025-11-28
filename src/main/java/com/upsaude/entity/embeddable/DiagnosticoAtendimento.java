package com.upsaude.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe embeddable para informações de diagnóstico do atendimento.
 *
 * @author UPSaúde
 */
@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiagnosticoAtendimento {

    @Column(name = "diagnostico", columnDefinition = "TEXT")
    private String diagnostico; // Diagnóstico principal

    @Column(name = "diagnosticos_secundarios", columnDefinition = "TEXT")
    private String diagnosticosSecundarios; // Diagnósticos secundários

    @Column(name = "hipotese_diagnostica", columnDefinition = "TEXT")
    private String hipoteseDiagnostica; // Hipótese diagnóstica

    @Column(name = "conduta", columnDefinition = "TEXT")
    private String conduta; // Conduta adotada

    @Column(name = "evolucao", columnDefinition = "TEXT")
    private String evolucao; // Evolução do paciente durante o atendimento
}

