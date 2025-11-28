package com.upsaude.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Entidade que representa a ligação entre um paciente e uma deficiência.
 * Armazena informações sobre a deficiência do paciente, incluindo laudo e diagnóstico.
 *
 * @author UPSaúde
 */
@Entity
@Table(name = "deficiencias_paciente", schema = "public")
@Data
@EqualsAndHashCode(callSuper = true)
public class DeficienciasPaciente extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deficiencia_id", nullable = false)
    private Deficiencias deficiencia;

    @Column(name = "possui_laudo", nullable = false)
    private Boolean possuiLaudo = false;

    @Column(name = "data_diagnostico")
    private java.time.LocalDate dataDiagnostico;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;
}

