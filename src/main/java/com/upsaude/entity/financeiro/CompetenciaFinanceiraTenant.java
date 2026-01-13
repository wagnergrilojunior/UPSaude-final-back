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
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(
    name = "competencia_financeira_tenant",
    schema = "public",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_competencia_tenant_tenant_competencia", columnNames = {"tenant_id", "competencia_id"})
    },
    indexes = {
        @Index(name = "idx_competencia_tenant_status", columnList = "tenant_id, status")
    }
)
@Data
@EqualsAndHashCode(callSuper = true)
public class CompetenciaFinanceiraTenant extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "competencia_id", nullable = false)
    private CompetenciaFinanceira competencia;

    @Column(name = "status", nullable = false, length = 20)
    private String status; // ABERTA | FECHADA

    @Column(name = "fechada_em")
    private OffsetDateTime fechadaEm;

    @Column(name = "fechada_por")
    private UUID fechadaPor;

    @Column(name = "motivo_fechamento", columnDefinition = "TEXT")
    private String motivoFechamento;

    @Column(name = "snapshot_hash", length = 64)
    private String snapshotHash; // Para integridade do fechamento

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "documento_bpa_fechamento_id")
    private com.upsaude.entity.faturamento.DocumentoFaturamento documentoBpaFechamento;

    @Column(name = "hash_movimentacoes", length = 64)
    private String hashMovimentacoes; // Hash das movimentações no momento do fechamento

    @Column(name = "hash_bpa", length = 64)
    private String hashBpa; // Hash dos dados BPA consolidados

    @Column(name = "validacao_integridade")
    private Boolean validacaoIntegridade; // Flag indicando se hashMovimentacoes == hashBpa
}
