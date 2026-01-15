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

import java.time.LocalDate;
import java.time.OffsetDateTime;
import com.upsaude.entity.sistema.usuario.UsuariosSistema;

@Entity
@Table(
    name = "competencia_financeira",
    schema = "public",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_competencia_financeira_tenant_codigo", columnNames = {"tenant_id", "codigo"})
    },
    indexes = {
        @Index(name = "idx_competencia_financeira_tenant_data", columnList = "tenant_id, data_inicio, data_fim"),
        @Index(name = "idx_competencia_financeira_tenant_status", columnList = "tenant_id, status")
    }
)
@Data
@EqualsAndHashCode(callSuper = true)
public class CompetenciaFinanceira extends BaseEntityFinanceiro {

    @Column(name = "codigo", nullable = false, length = 20)
    private String codigo;

    @Column(name = "tipo", nullable = false, length = 20)
    private String tipo;

    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    @Column(name = "data_fim", nullable = false)
    private LocalDate dataFim;

    @Column(name = "descricao", length = 500)
    private String descricao;

    @Column(name = "status", nullable = false, length = 20)
    private String status = "ABERTA";

    @Column(name = "fechada_em")
    private OffsetDateTime fechadaEm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fechada_por")
    private UsuariosSistema fechadaPor;

    @Column(name = "motivo_fechamento", columnDefinition = "TEXT")
    private String motivoFechamento;

    @Column(name = "snapshot_hash", length = 64)
    private String snapshotHash;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "documento_bpa_fechamento_id")
    private com.upsaude.entity.faturamento.DocumentoFaturamento documentoBpaFechamento;

    @Column(name = "hash_movimentacoes", length = 64)
    private String hashMovimentacoes;

    @Column(name = "hash_bpa", length = 64)
    private String hashBpa;

    @Column(name = "validacao_integridade")
    private Boolean validacaoIntegridade;

    public boolean isFechada() {
        return "FECHADA".equalsIgnoreCase(status);
    }

    public boolean isAberta() {
        return "ABERTA".equalsIgnoreCase(status);
    }
}
