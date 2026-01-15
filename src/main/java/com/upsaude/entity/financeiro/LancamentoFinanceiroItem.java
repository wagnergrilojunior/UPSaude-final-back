package com.upsaude.entity.financeiro;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;


@Entity
@Table(
    name = "lancamento_financeiro_item",
    schema = "public",
    indexes = {
        @Index(name = "idx_lancamento_item_lancamento", columnList = "lancamento_id"),
        @Index(name = "idx_lancamento_item_conta", columnList = "conta_contabil_id")
    }
)
@Data
@EqualsAndHashCode(callSuper = true)
public class LancamentoFinanceiroItem extends BaseEntityFinanceiro {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lancamento_id", nullable = false)
    private LancamentoFinanceiro lancamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conta_contabil_id", nullable = false)
    private ContaContabil contaContabil;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "centro_custo_id")
    private CentroCusto centroCusto;

    @Column(name = "tipo_partida", nullable = false, length = 20)
private String tipoPartida;

    @Positive
    @Column(name = "valor", nullable = false, precision = 14, scale = 2)
    private BigDecimal valor;

    @Column(name = "historico", columnDefinition = "TEXT")
    private String historico;
}
