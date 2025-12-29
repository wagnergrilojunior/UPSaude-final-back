package com.upsaude.entity.profissional.equipe;
import com.upsaude.entity.profissional.Medicos;
import com.upsaude.entity.BaseEntity;

import com.upsaude.entity.profissional.ProfissionaisSaude;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

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
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class EscalaTrabalho extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profissional_id", nullable = false)
    private ProfissionaisSaude profissional;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id")
    private Medicos medico;

    @Column(name = "data_inicio")
    private java.time.LocalDate dataInicio;

    @Column(name = "data_fim")
    private java.time.LocalDate dataFim;

    @Column(name = "dia_semana", nullable = false)
    private DayOfWeek diaSemana;

    @Column(name = "hora_entrada", nullable = false)
    private LocalTime horaEntrada;

    @Column(name = "hora_saida", nullable = false)
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
