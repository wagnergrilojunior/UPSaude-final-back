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

    @Column(name = "codigo_cid_principal", length = 10)
    private String codigoCidPrincipal;

    @Column(name = "codigo_cid_secundario", length = 10)
    private String codigoCidSecundario;

    @Column(name = "diagnosticos_secundarios_json", columnDefinition = "TEXT")
    private String diagnosticosSecundariosJson;

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
