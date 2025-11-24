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
@Table(name = "consultas", schema = "public")
@Data
public class Consultas extends BaseEntity {

    

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estabelecimento_id")
    private Estabelecimentos estabelecimento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id")
    private Medicos medico;

    @Column(name = "data_consulta", nullable = false)
    private OffsetDateTime dataConsulta;

    @Column(name = "duracao_minutos")
    private Integer duracaoMinutos;

    @Column(name = "tipo_consulta", length = 100)
    private String tipoConsulta;

    @Column(name = "status", length = 50)
    private String status;

    @Column(name = "motivo", columnDefinition = "TEXT")
    private String motivo;

    @Column(name = "diagnostico", columnDefinition = "TEXT")
    private String diagnostico;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;
}
