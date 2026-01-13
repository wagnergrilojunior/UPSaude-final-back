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
import java.time.LocalDate;

@Entity
@Table(
    name = "pagamento_pagar",
    schema = "public",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_pagamento_pagar_idempotency", columnNames = {"tenant_id", "idempotency_key"})
    },
    indexes = {
        @Index(name = "idx_pagamento_pagar_titulo", columnList = "titulo_pagar_id")
    }
)
@Data
@EqualsAndHashCode(callSuper = true)
public class PagamentoPagar extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "titulo_pagar_id", nullable = false)
    private TituloPagar tituloPagar;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conta_financeira_id", nullable = false)
    private ContaFinanceira contaFinanceira;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movimentacao_conta_id")
    private MovimentacaoConta movimentacaoConta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lancamento_financeiro_id")
    private LancamentoFinanceiro lancamentoFinanceiro;

    @Positive
    @Column(name = "valor_pago", nullable = false, precision = 14, scale = 2)
    private BigDecimal valorPago;

    @Column(name = "data_pagamento", nullable = false)
    private LocalDate dataPagamento;

    @Column(name = "meio_pagamento", length = 30)
    private String meioPagamento; // PIX | BOLETO | TED | DINHEIRO | CARTAO | OUTRO

    @Column(name = "status", nullable = false, length = 30)
    private String status; // EFETIVADO | CANCELADO_POR_REVERSAO

    @Column(name = "idempotency_key", nullable = false, length = 255)
    private String idempotencyKey;
}
