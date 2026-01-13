package com.upsaude.entity.embeddable;

import com.upsaude.entity.referencia.cid.Cid10Subcategorias;
import com.upsaude.entity.referencia.Ciap2;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "main_cid10_id")
    private Cid10Subcategorias mainCid10;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "main_ciap2_id")
    private Ciap2 mainCiap2;

    @Column(name = "main_clinical_status", length = 20)
    private String mainClinicalStatus;

    @Column(name = "main_verification_status", length = 20)
    private String mainVerificationStatus;

    @Column(name = "diagnosticos_secundarios", columnDefinition = "TEXT")
    private String diagnosticosSecundarios;

    @Column(name = "hipotese_diagnostica", columnDefinition = "TEXT")
    private String hipoteseDiagnostica;

    @Column(name = "conduta", columnDefinition = "TEXT")
    private String conduta;

    @Column(name = "evolucao", columnDefinition = "TEXT")
    private String evolucao;
}
