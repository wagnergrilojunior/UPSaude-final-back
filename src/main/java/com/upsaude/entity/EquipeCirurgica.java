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

/**
 * Entidade que representa um membro da equipe cirúrgica.
 * Permite registrar todos os profissionais que participaram da cirurgia.
 *
 * @author UPSaúde
 */
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

    // ========== RELACIONAMENTOS ==========

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
    private Medicos medico; // Opcional: médico específico

    // ========== FUNÇÃO NA CIRURGIA ==========

    @Column(name = "funcao", nullable = false, length = 100)
    @NotNull(message = "Função na cirurgia é obrigatória")
    @Size(max = 100, message = "Função deve ter no máximo 100 caracteres")
    private String funcao; // Ex: Cirurgião Principal, Assistente, Anestesista, Enfermeiro, etc.

    @Column(name = "eh_principal")
    private Boolean ehPrincipal; // Se é o profissional principal na função

    // ========== REMUNERAÇÃO ==========

    @Column(name = "valor_participacao", precision = 10, scale = 2)
    private BigDecimal valorParticipacao; // Valor recebido pela participação

    @Column(name = "percentual_participacao", precision = 5, scale = 2)
    private BigDecimal percentualParticipacao; // Percentual de participação no valor total

    // ========== HORÁRIOS DE PARTICIPAÇÃO ==========

    @Column(name = "data_hora_entrada")
    private java.time.OffsetDateTime dataHoraEntrada; // Quando entrou na cirurgia

    @Column(name = "data_hora_saida")
    private java.time.OffsetDateTime dataHoraSaida; // Quando saiu da cirurgia

    // ========== OBSERVAÇÕES ==========

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;
}

