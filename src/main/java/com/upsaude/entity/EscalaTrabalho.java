package com.upsaude.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@Table(name = "escala_trabalho", schema = "public",
       indexes = {
           @Index(name = "idx_escala_profissional", columnList = "profissional_id"),
           @Index(name = "idx_escala_dia_semana", columnList = "dia_semana"),
           @Index(name = "idx_escala_estabelecimento", columnList = "estabelecimento_id"),
           @Index(name = "idx_escala_periodo", columnList = "data_inicio,data_fim")
       })
@Data
@EqualsAndHashCode(callSuper = true)
public class EscalaTrabalho extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profissional_id", nullable = false)
    @NotNull(message = "Profissional de saúde é obrigatório")
    private ProfissionaisSaude profissional;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id")
    private Medicos medico;

    @Column(name = "data_inicio")
    private java.time.LocalDate dataInicio;

    @Column(name = "data_fim")
    private java.time.LocalDate dataFim;

    @Column(name = "dia_semana", nullable = false)
    @NotNull(message = "Dia da semana é obrigatório")
    private DayOfWeek diaSemana;

    @Column(name = "hora_entrada", nullable = false)
    @NotNull(message = "Hora de entrada é obrigatória")
    private LocalTime horaEntrada;

    @Column(name = "hora_saida", nullable = false)
    @NotNull(message = "Hora de saída é obrigatória")
    private LocalTime horaSaida;

    @Column(name = "intervalo_inicio")
    private LocalTime intervaloInicio;

    @Column(name = "intervalo_fim")
    private LocalTime intervaloFim;

    @Column(name = "carga_horaria_diaria")
    private Integer cargaHorariaDiaria;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;
}
