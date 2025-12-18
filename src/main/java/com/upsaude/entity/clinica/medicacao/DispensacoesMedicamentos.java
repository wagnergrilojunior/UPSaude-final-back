package com.upsaude.entity.clinica.medicacao;
import com.upsaude.entity.BaseEntity;

import com.upsaude.entity.paciente.Paciente;

import java.time.OffsetDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "dispensacoes_medicamentos", schema = "public")
@Data
public class DispensacoesMedicamentos extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medicamento_id", nullable = false)
    private Medicacao medicacao;

    @Column(name = "quantidade")
    private Integer quantidade;

    @Column(name = "data_dispensacao")
    private OffsetDateTime dataDispensacao;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;
}
