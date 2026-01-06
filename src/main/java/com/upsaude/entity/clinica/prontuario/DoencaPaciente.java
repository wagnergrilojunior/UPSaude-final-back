package com.upsaude.entity.clinica.prontuario;

import com.upsaude.entity.BaseEntity;
import com.upsaude.entity.referencia.cid.Cid10Subcategorias;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Entity
@Table(name = "doencas_paciente", schema = "public",
       indexes = {
           @Index(name = "idx_doenca_paciente_prontuario", columnList = "prontuario_id"),
           @Index(name = "idx_doenca_paciente_cid10", columnList = "cid10_subcategorias_id"),
           @Index(name = "idx_doenca_paciente_data_diagnostico", columnList = "data_diagnostico"),
           @Index(name = "idx_doenca_paciente_ativa", columnList = "ativa")
       })
@Data
@EqualsAndHashCode(callSuper = true)
public class DoencaPaciente extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prontuario_id", nullable = false)
    private Prontuario prontuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cid10_subcategorias_id", nullable = false)
    private Cid10Subcategorias diagnostico;

    @Column(name = "data_diagnostico", nullable = false)
    private LocalDate dataDiagnostico;

    @Column(name = "ativa", nullable = false)
    private Boolean ativa;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;
}

