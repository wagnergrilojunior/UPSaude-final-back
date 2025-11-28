package com.upsaude.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe embeddable para informações de anamnese do atendimento.
 *
 * @author UPSaúde
 */
@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnamneseAtendimento {

    @Column(name = "queixa_principal", columnDefinition = "TEXT")
    private String queixaPrincipal; // Queixa principal do paciente

    @Column(name = "historia_doenca_atual", columnDefinition = "TEXT")
    private String historiaDoencaAtual; // História da doença atual (HDA)

    @Column(name = "antecedentes_relevantes", columnDefinition = "TEXT")
    private String antecedentesRelevantes; // Antecedentes relevantes para o atendimento

    @Column(name = "medicamentos_uso", columnDefinition = "TEXT")
    private String medicamentosUso; // Medicamentos em uso

    @Column(name = "alergias_conhecidas", columnDefinition = "TEXT")
    private String alergiasConhecidas; // Alergias conhecidas

    @Column(name = "exame_fisico", columnDefinition = "TEXT")
    private String exameFisico; // Exame físico realizado

    @Column(name = "sinais_vitais", columnDefinition = "TEXT")
    private String sinaisVitais; // Sinais vitais (PA, FC, FR, T, SatO2, etc.)

    @Column(name = "observacoes_anamnese", columnDefinition = "TEXT")
    private String observacoesAnamnese; // Observações adicionais da anamnese
}

