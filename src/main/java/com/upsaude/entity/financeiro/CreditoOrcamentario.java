package com.upsaude.entity.financeiro;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(
    name = "credito_orcamentario",
    schema = "public",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_credito_orcamentario_tenant_idempotency", columnNames = {"tenant_id", "idempotency_key"})
    },
    indexes = {
        @Index(name = "idx_credito_orcamentario_competencia", columnList = "tenant_id, competencia_id")
    }
)
@Data
@EqualsAndHashCode(callSuper = true)
public class CreditoOrcamentario extends BaseEntityFinanceiro {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "competencia_id", nullable = false)
    private CompetenciaFinanceira competencia;

    @Column(name = "valor", nullable = false, precision = 14, scale = 2)
    private BigDecimal valor;

    @Column(name = "fonte", nullable = false, length = 50)
private String fonte;

    @Column(name = "documento_referencia", length = 255)
    private String documentoReferencia;

    @Column(name = "data_credito", nullable = false)
    private LocalDate dataCredito;

    @Column(name = "idempotency_key", nullable = false, length = 255)
    private String idempotencyKey;
}
