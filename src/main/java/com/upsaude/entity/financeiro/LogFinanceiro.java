package com.upsaude.entity.financeiro;

import com.upsaude.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@Table(
    name = "log_financeiro",
    schema = "public",
    indexes = {
        @Index(name = "idx_log_financeiro_entidade", columnList = "tenant_id, entidade_tipo, entidade_id, criado_em"),
        @Index(name = "idx_log_financeiro_data", columnList = "tenant_id, criado_em")
    }
)
@Data
@EqualsAndHashCode(callSuper = true)
public class LogFinanceiro extends BaseEntity {

    @Column(name = "entidade_tipo", nullable = false, length = 50)
    private String entidadeTipo; // LANCAMENTO | TITULO_RECEBER | BAIXA_RECEBER | TITULO_PAGAR | PAGAMENTO_PAGAR | DOCUMENTO_FATURAMENTO | CONCILIACAO | CONTA_FINANCEIRA | ORCAMENTO_COMPETENCIA | RESERVA_ORCAMENTARIA | OUTRO

    @Column(name = "entidade_id", nullable = false)
    private UUID entidadeId;

    @Column(name = "acao", nullable = false, length = 50)
    private String acao; // CRIAR | REVERSAO | AJUSTE | CONCILIAR | IMPORTAR_EXTRATO | FECHAR_COMPETENCIA | REABRIR_COMPETENCIA | OUTRO

    @Column(name = "usuario_id")
    private UUID usuarioId;

    @Column(name = "correlation_id", length = 100)
    private String correlationId;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "payload_antes", columnDefinition = "jsonb")
    private String payloadAntes;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "payload_depois", columnDefinition = "jsonb")
    private String payloadDepois;

    @Column(name = "ip", length = 50)
    private String ip;

    @Column(name = "user_agent", length = 500)
    private String userAgent;
}
