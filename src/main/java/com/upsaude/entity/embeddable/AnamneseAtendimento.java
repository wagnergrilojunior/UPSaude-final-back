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

    @Column(name = "observacoes_anamnese", columnDefinition = "TEXT")
    private String observacoesAnamnese;
}
