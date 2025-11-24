package com.upsaude.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "doencas_cronicas_paciente", schema = "public")
@Data
public class DoencasCronicasPaciente extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doenca_cronica_id", nullable = false)
    private DoencasCronicas doencaCronica;

    @Column(name = "data_diagnostico")
    private java.time.LocalDate dataDiagnostico;

    @Column(name = "medicacao_atual", columnDefinition = "TEXT")
    private String medicacaoAtual;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;
}
