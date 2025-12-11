package com.upsaude.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Entity
@Table(name = "equipe_cirurgica", schema = "public",
       indexes = {
           @Index(name = "idx_equipe_cirurgica_cirurgia", columnList = "cirurgia_id"),
           @Index(name = "idx_equipe_cirurgica_profissional", columnList = "profissional_id"),
           @Index(name = "idx_equipe_cirurgica_funcao", columnList = "funcao")
       })
@Data
@EqualsAndHashCode(callSuper = true)
public class EquipeCirurgica extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cirurgia_id", nullable = false)
    @NotNull(message = "Cirurgia é obrigatória")
    private Cirurgia cirurgia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profissional_id", nullable = false)
    @NotNull(message = "Profissional é obrigatório")
    private ProfissionaisSaude profissional;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id")
    private Medicos medico;

    @Column(name = "funcao", nullable = false, length = 100)
    @NotNull(message = "Função na cirurgia é obrigatória")
    @Size(max = 100, message = "Função deve ter no máximo 100 caracteres")
    private String funcao;

    @Column(name = "eh_principal")
    private Boolean ehPrincipal;

    @Column(name = "valor_participacao", precision = 10, scale = 2)
    private BigDecimal valorParticipacao;

    @Column(name = "percentual_participacao", precision = 5, scale = 2)
    private BigDecimal percentualParticipacao;

    @Column(name = "data_hora_entrada")
    private java.time.OffsetDateTime dataHoraEntrada;

    @Column(name = "data_hora_saida")
    private java.time.OffsetDateTime dataHoraSaida;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;
}
