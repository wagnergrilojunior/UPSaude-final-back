package com.upsaude.entity.financeiro;

import com.upsaude.entity.BaseEntity;
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
    name = "movimentacao_conta",
    schema = "public",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_movimentacao_conta_conta_idempotency", columnNames = {"conta_financeira_id", "idempotency_key"})
    },
    indexes = {
        @Index(name = "idx_movimentacao_conta_data", columnList = "conta_financeira_id, data_movimento")
    }
)
@Data
@EqualsAndHashCode(callSuper = true)
public class MovimentacaoConta extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conta_financeira_id", nullable = false)
    private ContaFinanceira contaFinanceira;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "baixa_receber_id")
    private BaixaReceber baixaReceber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pagamento_pagar_id")
    private PagamentoPagar pagamentoPagar;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transferencia_id")
    private TransferenciaEntreContas transferencia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lancamento_financeiro_id")
    private LancamentoFinanceiro lancamentoFinanceiro;

    @Column(name = "tipo", nullable = false, length = 20)
    private String tipo; // ENTRADA | SAIDA

    @Positive
    @Column(name = "valor", nullable = false, precision = 14, scale = 2)
    private BigDecimal valor;

    @Column(name = "data_movimento", nullable = false)
    private OffsetDateTime dataMovimento;

    @Column(name = "status", nullable = false, length = 30)
    private String status; // PENDENTE | EFETIVADO | CANCELADO_POR_REVERSAO

    @Column(name = "idempotency_key", nullable = false, length = 255)
    private String idempotencyKey;
}
