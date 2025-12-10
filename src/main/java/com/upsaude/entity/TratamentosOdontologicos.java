package com.upsaude.entity;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Entity
@Table(name = "tratamentos_odontologicos", schema = "public")
@Data
public class TratamentosOdontologicos extends BaseEntity {

    /**
     * Construtor padrão que inicializa as coleções para evitar NullPointerException.
     */
    public TratamentosOdontologicos() {
        this.procedimentos = new ArrayList<>();
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profissional_id")
    private ProfissionaisSaude profissional;

    @Column(name = "titulo", nullable = false, length = 255)
    private String titulo;

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "data_inicio")
    private OffsetDateTime dataInicio;

    @Column(name = "data_fim")
    private OffsetDateTime dataFim;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status")
    private StatusTratamento status;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    @OneToMany(mappedBy = "tratamento", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TratamentosProcedimentos> procedimentos = new ArrayList<>();

    // ========== MÉTODOS DE CICLO DE VIDA ==========

    /**
     * Garante que as coleções não sejam nulas antes de persistir ou atualizar.
     * Recria a lista se estiver nula.
     */
    @PrePersist
    @PreUpdate
    public void validateCollections() {
        if (procedimentos == null) {
            procedimentos = new ArrayList<>();
        }
    }

    // ========== MÉTODOS UTILITÁRIOS - PROCEDIMENTOS ==========

    /**
     * Adiciona um procedimento ao tratamento com sincronização bidirecional.
     * Garante que o procedimento também referencia este tratamento.
     *
     * @param procedimento O procedimento a ser adicionado
     */
    public void addProcedimento(TratamentosProcedimentos procedimento) {
        if (procedimento == null) {
            return;
        }
        if (procedimentos == null) {
            procedimentos = new ArrayList<>();
        }
        if (!procedimentos.contains(procedimento)) {
            procedimentos.add(procedimento);
            procedimento.setTratamento(this);
        }
    }

    /**
     * Remove um procedimento do tratamento com sincronização bidirecional.
     * Remove a referência do procedimento para este tratamento.
     *
     * @param procedimento O procedimento a ser removido
     */
    public void removeProcedimento(TratamentosProcedimentos procedimento) {
        if (procedimento == null || procedimentos == null) {
            return;
        }
        if (procedimentos.remove(procedimento)) {
            procedimento.setTratamento(null);
        }
    }

    public enum StatusTratamento {
        PLANEJADO,
        EM_ANDAMENTO,
        CONCLUIDO,
        CANCELADO
    }
}
