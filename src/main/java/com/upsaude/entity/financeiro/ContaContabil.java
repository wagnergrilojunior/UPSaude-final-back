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

@Entity
@Table(
    name = "conta_contabil",
    schema = "public",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_conta_contabil_plano_codigo", columnNames = {"plano_contas_id", "codigo"})
    },
    indexes = {
        @Index(name = "idx_conta_contabil_tenant_plano", columnList = "tenant_id, plano_contas_id"),
        @Index(name = "idx_conta_contabil_tenant_pai", columnList = "tenant_id, plano_contas_id, conta_pai_id")
    }
)
@Data
@EqualsAndHashCode(callSuper = true)
public class ContaContabil extends BaseEntityFinanceiro {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plano_contas_id", nullable = false)
    private PlanoContas planoContas;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conta_pai_id")
    private ContaContabil contaPai;

    @Column(name = "codigo", nullable = false, length = 50)
    private String codigo;

    @Column(name = "nome", nullable = false, length = 255)
    private String nome;

    @Column(name = "natureza", nullable = false, length = 20)
private String natureza;

    @Column(name = "aceita_lancamento", nullable = false)
    private Boolean aceitaLancamento = false;

    @Column(name = "nivel")
    private Integer nivel;

    @Column(name = "ordem")
    private Integer ordem;
}
