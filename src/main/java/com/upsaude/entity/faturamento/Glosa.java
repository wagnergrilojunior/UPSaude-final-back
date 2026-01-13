package com.upsaude.entity.faturamento;

import com.upsaude.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Entity
@Table(
    name = "glosa",
    schema = "public",
    indexes = {
        @Index(name = "idx_glosa_documento", columnList = "documento_id"),
        @Index(name = "idx_glosa_status", columnList = "status")
    }
)
@Data
@EqualsAndHashCode(callSuper = true)
public class Glosa extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "documento_id", nullable = false)
    private DocumentoFaturamento documento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private DocumentoFaturamentoItem item;

    @Column(name = "tipo", nullable = false, length = 20)
    private String tipo; // TOTAL | PARCIAL

    @Column(name = "valor_glosado", nullable = false, precision = 14, scale = 2)
    private BigDecimal valorGlosado;

    @Column(name = "motivo_codigo", length = 50)
    private String motivoCodigo;

    @Column(name = "motivo_descricao", columnDefinition = "TEXT")
    private String motivoDescricao;

    @Column(name = "status", nullable = false, length = 30)
    private String status; // ABERTA | CONTESTADA | ACEITA | REVERTIDA
}
