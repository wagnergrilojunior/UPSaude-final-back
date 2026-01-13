package com.upsaude.entity.financeiro;

import com.upsaude.entity.BaseEntity;
import com.upsaude.entity.faturamento.DocumentoFaturamento;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
    name = "titulo_receber",
    schema = "public",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_titulo_receber_tenant_idempotency", columnNames = {"tenant_id", "idempotency_key"})
    },
    indexes = {
        @Index(name = "idx_titulo_receber_status_vencimento", columnList = "tenant_id, status, data_vencimento")
    }
)
@Data
@EqualsAndHashCode(callSuper = true)
public class TituloReceber extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "documento_faturamento_id")
    private DocumentoFaturamento documentoFaturamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pagador_id", nullable = false)
    private ParteFinanceira pagador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conta_contabil_receita_id", nullable = false)
    private ContaContabil contaContabilReceita;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "centro_custo_id")
    private CentroCusto centroCusto;

    @Column(name = "numero", nullable = false, length = 100)
    private String numero;

    @Column(name = "parcela")
    private Integer parcela;

    @Column(name = "total_parcelas")
    private Integer totalParcelas;

    @Column(name = "valor_original", nullable = false, precision = 14, scale = 2)
    private BigDecimal valorOriginal;

    @Column(name = "desconto", precision = 14, scale = 2)
    private BigDecimal desconto;

    @Column(name = "juros", precision = 14, scale = 2)
    private BigDecimal juros;

    @Column(name = "multa", precision = 14, scale = 2)
    private BigDecimal multa;

    @Column(name = "valor_aberto", nullable = false, precision = 14, scale = 2)
    private BigDecimal valorAberto;

    @Column(name = "data_emissao", nullable = false)
    private LocalDate dataEmissao;

    @Column(name = "data_vencimento", nullable = false)
    private LocalDate dataVencimento;

    @Column(name = "status", nullable = false, length = 30)
    private String status; // ABERTO | PARCIAL | PAGO | CANCELADO_POR_REVERSAO | RENEGOCIADO

    @Column(name = "idempotency_key", nullable = false, length = 255)
    private String idempotencyKey;

    @OneToMany(mappedBy = "tituloReceber", fetch = FetchType.LAZY)
    private List<BaixaReceber> baixas = new ArrayList<>();
}
