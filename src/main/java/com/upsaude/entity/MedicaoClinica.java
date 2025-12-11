package com.upsaude.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "medicoes_clinicas", schema = "public")
@Data
@EqualsAndHashCode(callSuper = true)
public class MedicaoClinica extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @Column(name = "data_hora", nullable = false)
    private OffsetDateTime dataHora;

    @Column(name = "pressao_sistolica")
    private Integer pressaoSistolica;

    @Column(name = "pressao_diastolica")
    private Integer pressaoDiastolica;

    @Column(name = "frequencia_cardiaca")
    private Integer frequenciaCardiaca;

    @Column(name = "frequencia_respiratoria")
    private Integer frequenciaRespiratoria;

    @Column(name = "temperatura", precision = 5, scale = 2)
    private BigDecimal temperatura;

    @Column(name = "saturacao_oxigenio")
    private Integer saturacaoOxigenio;

    @Column(name = "glicemia_capilar", precision = 6, scale = 2)
    private BigDecimal glicemiaCapilar;

    @Column(name = "peso", precision = 5, scale = 2)
    private BigDecimal peso;

    @Column(name = "altura", precision = 4, scale = 2)
    private BigDecimal altura;

    @Column(name = "circunferencia_abdominal", precision = 6, scale = 2)
    private BigDecimal circunferenciaAbdominal;

    @Column(name = "imc", precision = 4, scale = 2)
    private BigDecimal imc;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;
}
