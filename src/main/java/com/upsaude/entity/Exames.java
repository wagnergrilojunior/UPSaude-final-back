package com.upsaude.entity;

import java.time.OffsetDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "exames", schema = "public")
@Data
public class Exames extends BaseEntity {

    

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @Column(name = "tipo_exame", length = 100)
    private String tipoExame;

    @Column(name = "data_exame")
    private OffsetDateTime dataExame;

    @Column(name = "resultados", columnDefinition = "jsonb")
    private String resultados;
}
