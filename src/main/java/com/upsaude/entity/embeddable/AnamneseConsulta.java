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
    private String queixaPrincipal;

    @Column(name = "historia_doenca_atual", columnDefinition = "TEXT")
    private String historiaDoencaAtual;

    @Column(name = "antecedentes_pessoais", columnDefinition = "TEXT")
    private String antecedentesPessoais;

    @Column(name = "antecedentes_familiares", columnDefinition = "TEXT")
    private String antecedentesFamiliares;

    @Column(name = "medicamentos_uso", columnDefinition = "TEXT")
    private String medicamentosUso;

    @Column(name = "alergias", columnDefinition = "TEXT")
    private String alergias;

    @Column(name = "habitos_vida", columnDefinition = "TEXT")
    private String habitosVida;

    @Column(name = "exame_fisico", columnDefinition = "TEXT")
    private String exameFisico;

    @Column(name = "sinais_vitais", columnDefinition = "TEXT")
    private String sinaisVitais;
}
