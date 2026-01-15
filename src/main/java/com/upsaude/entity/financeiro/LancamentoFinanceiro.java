package com.upsaude.entity.financeiro;

import com.upsaude.entity.sistema.usuario.UsuariosSistema;
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

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(
    name = "lancamento_financeiro",
    schema = "public",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_lancamento_financeiro_tenant_idempotency", columnNames = {"tenant_id", "idempotency_key"})
    },
    indexes = {
        @Index(name = "idx_lancamento_financeiro_competencia", columnList = "tenant_id, competencia_id, data_evento"),
        @Index(name = "idx_lancamento_financeiro_origem", columnList = "tenant_id, origem_tipo, origem_id"),
        @Index(name = "idx_lancamento_financeiro_prestador", columnList = "tenant_id, prestador_tipo, prestador_id"),
        @Index(name = "idx_lancamento_financeiro_estorno", columnList = "tenant_id, status, motivo_estorno")
    }
)
@Data
@EqualsAndHashCode(callSuper = true)
public class LancamentoFinanceiro extends BaseEntityFinanceiro {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "competencia_id", nullable = false)
    private CompetenciaFinanceira competencia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "documento_faturamento_id")
    private com.upsaude.entity.faturamento.DocumentoFaturamento documentoFaturamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "titulo_receber_id")
    private TituloReceber tituloReceber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "titulo_pagar_id")
    private TituloPagar tituloPagar;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conciliacao_id")
    private ConciliacaoBancaria conciliacao;

    @Column(name = "origem_tipo", nullable = false, length = 50)
private String origemTipo;

    @Column(name = "origem_id")
    private UUID origemId;

    @Column(name = "status", nullable = false, length = 30)
private String status;

    @Column(name = "data_evento", nullable = false)
    private OffsetDateTime dataEvento;

    @Column(name = "descricao", length = 500)
    private String descricao;

    @Column(name = "idempotency_key", nullable = false, length = 255)
    private String idempotencyKey;

    @Column(name = "grupo_lancamento")
    private UUID grupoLancamento;

    @Column(name = "travado", nullable = false)
    private Boolean travado = false;

    @Column(name = "travado_em")
    private OffsetDateTime travadoEm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travado_por_id")
    private UsuariosSistema travadoPor;

    @Column(name = "motivo_estorno", length = 30)
private String motivoEstorno;

    @Column(name = "referencia_estorno_tipo", length = 50)
private String referenciaEstornoTipo;

    @Column(name = "referencia_estorno_id")
private UUID referenciaEstornoId;

    @Column(name = "prestador_id")
private UUID prestadorId;

    @Column(name = "prestador_tipo", length = 30)
private String prestadorTipo;

    @OneToMany(mappedBy = "lancamento", fetch = FetchType.LAZY)
    private List<LancamentoFinanceiroItem> itens = new ArrayList<>();
}
