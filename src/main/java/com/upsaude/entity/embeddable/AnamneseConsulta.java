package com.upsaude.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Classe embeddable para informações de anamnese da consulta.
 *
 * @author UPSaúde
 */
@Embeddable
@Data
@Builder
@AllArgsConstructor
public class AnamneseConsulta {

    public AnamneseConsulta() {
        this.queixaPrincipal = "";
        this.historiaDoencaAtual = "";
        this.antecedentesPessoais = "";
        this.antecedentesFamiliares = "";
        this.medicamentosUso = "";
        this.alergias = "";
        this.habitosVida = "";
        this.exameFisico = "";
        this.sinaisVitais = "";
    }

    @Column(name = "queixa_principal", columnDefinition = "TEXT")
    private String queixaPrincipal; // Queixa principal do paciente

    @Column(name = "historia_doenca_atual", columnDefinition = "TEXT")
    private String historiaDoencaAtual; // História da doença atual (HDA)

    @Column(name = "antecedentes_pessoais", columnDefinition = "TEXT")
    private String antecedentesPessoais; // Antecedentes pessoais

    @Column(name = "antecedentes_familiares", columnDefinition = "TEXT")
    private String antecedentesFamiliares; // Antecedentes familiares

    @Column(name = "medicamentos_uso", columnDefinition = "TEXT")
    private String medicamentosUso; // Medicamentos em uso

    @Column(name = "alergias", columnDefinition = "TEXT")
    private String alergias; // Alergias conhecidas

    @Column(name = "habitos_vida", columnDefinition = "TEXT")
    private String habitosVida; // Hábitos de vida (tabagismo, etilismo, etc.)

    @Column(name = "exame_fisico", columnDefinition = "TEXT")
    private String exameFisico; // Exame físico realizado

    @Column(name = "sinais_vitais", columnDefinition = "TEXT")
    private String sinaisVitais; // Sinais vitais (PA, FC, FR, T, SatO2, etc.)
}

