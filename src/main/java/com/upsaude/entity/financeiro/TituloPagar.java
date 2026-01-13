package com.upsaude.entity.financeiro;

import com.upsaude.entity.BaseEntity;
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
    name = "titulo_pagar",
    schema = "public",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_titulo_pagar_tenant_idempotency", columnNames = {"tenant_id", "idempotency_key"})
    },
    indexes = {
        @Index(name = "idx_titulo_pagar_status_vencimento", columnList = "tenant_id, status, data_vencimento")
    }
)
@Data
@EqualsAndHashCode(callSuper = true)
public class TituloPagar extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fornecedor_id", nullable = false)
    private ParteFinanceira fornecedor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conta_contabil_despesa_id", nullable = false)
    private ContaContabil contaContabilDespesa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "centro_custo_id", nullable = false)
    private CentroCusto centroCusto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recorrencia_financeira_id")
    private RecorrenciaFinanceira recorrenciaFinanceira;

    @Column(name = "numero_documento", nullable = false, length = 100)
    private String numeroDocumento;

    @Column(name = "valor_original", nullable = false, precision = 14, scale = 2)
    private BigDecimal valorOriginal;

    @Column(name = "valor_aberto", nullable = false, precision = 14, scale = 2)
    private BigDecimal valorAberto;

    @Column(name = "data_emissao", nullable = false)
    private LocalDate dataEmissao;

    @Column(name = "data_vencimento", nullable = false)
    private LocalDate dataVencimento;

    @Column(name = "status", nullable = false, length = 30)
    private String status; // ABERTO | PARCIAL | PAGO | CANCELADO_POR_REVERSAO

    @Column(name = "idempotency_key", nullable = false, length = 255)
    private String idempotencyKey;

    @OneToMany(mappedBy = "tituloPagar", fetch = FetchType.LAZY)
    private List<PagamentoPagar> pagamentos = new ArrayList<>();
}
