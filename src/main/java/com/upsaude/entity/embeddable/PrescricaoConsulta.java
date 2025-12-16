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
public class PrescricaoConsulta {

    public PrescricaoConsulta() {
        this.medicamentosPrescritos = "";
        this.orientacoes = "";
        this.dieta = "";
        this.atividadeFisica = "";
        this.repouso = "";
        this.outrasOrientacoes = "";
    }

    @Column(name = "medicamentos_prescritos", columnDefinition = "TEXT")
    private String medicamentosPrescritos;

    @Column(name = "orientacoes", columnDefinition = "TEXT")
    private String orientacoes;

    @Column(name = "dieta", columnDefinition = "TEXT")
    private String dieta;

    @Column(name = "atividade_fisica", columnDefinition = "TEXT")
    private String atividadeFisica;

    @Column(name = "repouso", columnDefinition = "TEXT")
    private String repouso;

    @Column(name = "outras_orientacoes", columnDefinition = "TEXT")
    private String outrasOrientacoes;
}
