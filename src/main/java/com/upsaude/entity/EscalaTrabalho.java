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

/**
 * Entidade que representa a escala de trabalho dos profissionais de saúde.
 * Define os dias da semana e horários em que o profissional trabalha.
 *
 * @author UPSaúde
 */
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

    // ========== RELACIONAMENTOS ==========

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profissional_id", nullable = false)
    @NotNull(message = "Profissional de saúde é obrigatório")
    private ProfissionaisSaude profissional;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id")
    private Medicos medico; // Opcional: para médicos específicos

    // ========== PERÍODO DA ESCALA ==========

    @Column(name = "data_inicio")
    private java.time.LocalDate dataInicio; // Início da vigência da escala

    @Column(name = "data_fim")
    private java.time.LocalDate dataFim; // Fim da vigência da escala (null = sem previsão de fim)

    // ========== DIA DA SEMANA E HORÁRIOS ==========

    @Column(name = "dia_semana", nullable = false)
    @NotNull(message = "Dia da semana é obrigatório")
    private DayOfWeek diaSemana; // 1=Segunda, 2=Terça, ..., 7=Domingo

    @Column(name = "hora_entrada", nullable = false)
    @NotNull(message = "Hora de entrada é obrigatória")
    private LocalTime horaEntrada;

    @Column(name = "hora_saida", nullable = false)
    @NotNull(message = "Hora de saída é obrigatória")
    private LocalTime horaSaida;

    // ========== INTERVALOS ==========

    @Column(name = "intervalo_inicio")
    private LocalTime intervaloInicio; // Início do intervalo (ex: almoço)

    @Column(name = "intervalo_fim")
    private LocalTime intervaloFim; // Fim do intervalo

    // ========== INFORMAÇÕES COMPLEMENTARES ==========

    @Column(name = "carga_horaria_diaria")
    private Integer cargaHorariaDiaria; // Carga horária em minutos do dia

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;
}

