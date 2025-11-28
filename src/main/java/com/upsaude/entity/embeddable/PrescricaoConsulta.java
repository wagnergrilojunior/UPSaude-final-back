package com.upsaude.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe embeddable para informações de prescrição da consulta.
 *
 * @author UPSaúde
 */
@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrescricaoConsulta {

    @Column(name = "medicamentos_prescritos", columnDefinition = "TEXT")
    private String medicamentosPrescritos; // Medicamentos prescritos

    @Column(name = "orientacoes", columnDefinition = "TEXT")
    private String orientacoes; // Orientações dadas ao paciente

    @Column(name = "dieta", columnDefinition = "TEXT")
    private String dieta; // Orientações dietéticas

    @Column(name = "atividade_fisica", columnDefinition = "TEXT")
    private String atividadeFisica; // Orientações sobre atividade física

    @Column(name = "repouso", columnDefinition = "TEXT")
    private String repouso; // Orientações sobre repouso

    @Column(name = "outras_orientacoes", columnDefinition = "TEXT")
    private String outrasOrientacoes; // Outras orientações
}

