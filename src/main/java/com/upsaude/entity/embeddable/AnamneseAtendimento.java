package com.upsaude.entity.embeddable;

import com.upsaude.entity.clinica.atendimento.SinalVital;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Embeddable
@Data
@Builder
@AllArgsConstructor
public class AnamneseAtendimento {

    public AnamneseAtendimento() {
        this.queixaPrincipal = "";
        this.historiaDoencaAtual = "";
        this.antecedentesRelevantes = "";
        this.medicamentosUso = "";
        this.alergiasConhecidas = "";
        this.exameFisico = "";
        this.sinaisVitais = "";
        this.observacoesAnamnese = "";
    }

    @Column(name = "queixa_principal", columnDefinition = "TEXT")
    private String queixaPrincipal;

    @Column(name = "historia_doenca_atual", columnDefinition = "TEXT")
    private String historiaDoencaAtual;

    @Column(name = "antecedentes_relevantes", columnDefinition = "TEXT")
    private String antecedentesRelevantes;

    @Column(name = "medicamentos_uso", columnDefinition = "TEXT")
    private String medicamentosUso;

    @Column(name = "alergias_conhecidas", columnDefinition = "TEXT")
    private String alergiasConhecidas;

    @Column(name = "exame_fisico", columnDefinition = "TEXT")
    private String exameFisico;

    @Column(name = "sinais_vitais", columnDefinition = "TEXT")
    private String sinaisVitais;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sinal_vital_record_id")
    private SinalVital sinalVitalRecord;

    @Column(name = "observacoes_anamnese", columnDefinition = "TEXT")
    private String observacoesAnamnese;
}
