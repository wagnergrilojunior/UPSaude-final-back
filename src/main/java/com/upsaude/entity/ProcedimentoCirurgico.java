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

import java.math.BigDecimal;

/**
 * Entidade que representa um procedimento realizado durante uma cirurgia.
 * Permite registrar múltiplos procedimentos em uma mesma cirurgia.
 *
 * @author UPSaúde
 */
@Entity
@Table(name = "procedimentos_cirurgicos", schema = "public",
       indexes = {
           @Index(name = "idx_proc_cirurgico_cirurgia", columnList = "cirurgia_id"),
           @Index(name = "idx_proc_cirurgico_codigo", columnList = "codigo_procedimento")
       })
@Data
@EqualsAndHashCode(callSuper = true)
public class ProcedimentoCirurgico extends BaseEntity {

    // ========== RELACIONAMENTOS ==========

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cirurgia_id", nullable = false)
    @NotNull(message = "Cirurgia é obrigatória")
    private Cirurgia cirurgia;

    // ========== DADOS DO PROCEDIMENTO ==========

    @Column(name = "descricao", nullable = false, columnDefinition = "TEXT")
    @NotNull(message = "Descrição do procedimento é obrigatória")
    private String descricao;

    @Column(name = "codigo_procedimento", length = 50)
    private String codigoProcedimento; // Código do procedimento (TUSS, SUS, etc.)

    @Column(name = "nome_procedimento", length = 500)
    private String nomeProcedimento; // Nome oficial do procedimento

    // ========== QUANTIDADE E VALOR ==========

    @Column(name = "quantidade", nullable = false)
    @NotNull(message = "Quantidade é obrigatória")
    private Integer quantidade = 1;

    @Column(name = "valor_unitario", precision = 10, scale = 2)
    private BigDecimal valorUnitario;

    @Column(name = "valor_total", precision = 10, scale = 2)
    private BigDecimal valorTotal; // valor_unitario * quantidade

    // ========== LADO (para procedimentos bilaterais) ==========

    @Column(name = "lado", length = 20)
    private String lado; // Esquerdo, Direito, Bilateral, etc.

    // ========== OBSERVAÇÕES ==========

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;
}

