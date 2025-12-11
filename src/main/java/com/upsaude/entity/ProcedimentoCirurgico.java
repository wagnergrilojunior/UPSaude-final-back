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

@Entity
@Table(name = "procedimentos_cirurgicos", schema = "public",
       indexes = {
           @Index(name = "idx_proc_cirurgico_cirurgia", columnList = "cirurgia_id"),
           @Index(name = "idx_proc_cirurgico_codigo", columnList = "codigo_procedimento")
       })
@Data
@EqualsAndHashCode(callSuper = true)
public class ProcedimentoCirurgico extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cirurgia_id", nullable = false)
    @NotNull(message = "Cirurgia é obrigatória")
    private Cirurgia cirurgia;

    @Column(name = "descricao", nullable = false, columnDefinition = "TEXT")
    @NotNull(message = "Descrição do procedimento é obrigatória")
    private String descricao;

    @Column(name = "codigo_procedimento", length = 50)
    private String codigoProcedimento;

    @Column(name = "nome_procedimento", length = 500)
    private String nomeProcedimento;

    @Column(name = "quantidade", nullable = false)
    @NotNull(message = "Quantidade é obrigatória")
    private Integer quantidade = 1;

    @Column(name = "valor_unitario", precision = 10, scale = 2)
    private BigDecimal valorUnitario;

    @Column(name = "valor_total", precision = 10, scale = 2)
    private BigDecimal valorTotal;

    @Column(name = "lado", length = 20)
    private String lado;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;
}
