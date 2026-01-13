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
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(
    name = "regra_classificacao_contabil",
    schema = "public",
    indexes = {
        @Index(name = "idx_regra_classificacao_tenant_escopo", columnList = "tenant_id, escopo, prioridade")
    }
)
@Data
@EqualsAndHashCode(callSuper = true)
public class RegraClassificacaoContabil extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conta_contabil_destino_id", nullable = false)
    private ContaContabil contaContabilDestino;

    @Column(name = "escopo", nullable = false, length = 50)
    private String escopo; // ASSISTENCIAL | FATURAMENTO | FINANCEIRO

    @Column(name = "prioridade", nullable = false)
    private Integer prioridade;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "condicao_jsonb", columnDefinition = "jsonb")
    private String condicaoJsonb; // JSON com critérios configuráveis
}
