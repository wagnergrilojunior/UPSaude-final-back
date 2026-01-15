package com.upsaude.entity.financeiro;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(
    name = "transferencia_entre_contas",
    schema = "public",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_transferencia_tenant_idempotency", columnNames = {"tenant_id", "idempotency_key"})
    },
    indexes = {
        @Index(name = "idx_transferencia_contas", columnList = "conta_origem_id, conta_destino_id")
    }
)
@Data
@EqualsAndHashCode(callSuper = true)
public class TransferenciaEntreContas extends BaseEntityFinanceiro {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conta_origem_id", nullable = false)
    private ContaFinanceira contaOrigem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conta_destino_id", nullable = false)
    private ContaFinanceira contaDestino;

    @Positive
    @Column(name = "valor", nullable = false, precision = 14, scale = 2)
    private BigDecimal valor;

    @Column(name = "data", nullable = false)
    private OffsetDateTime data;

    @Column(name = "status", nullable = false, length = 30)
private String status;

    @Column(name = "idempotency_key", nullable = false, length = 255)
    private String idempotencyKey;
}
