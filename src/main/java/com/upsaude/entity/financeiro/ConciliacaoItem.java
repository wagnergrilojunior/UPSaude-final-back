package com.upsaude.entity.financeiro;

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
    name = "conciliacao_item",
    schema = "public",
    indexes = {
        @Index(name = "idx_conciliacao_item_conciliacao", columnList = "conciliacao_id")
    }
)
@Data
@EqualsAndHashCode(callSuper = true)
public class ConciliacaoItem extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conciliacao_id", nullable = false)
    private ConciliacaoBancaria conciliacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "extrato_importado_id")
    private ExtratoBancarioImportado extratoImportado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movimentacao_conta_id")
    private MovimentacaoConta movimentacaoConta;

    @Column(name = "tipo_match", nullable = false, length = 20)
    private String tipoMatch; // AUTO | MANUAL

    @Column(name = "diferenca", precision = 14, scale = 2)
    private BigDecimal diferenca; // Para ajustes registrados como lançamentos
}
