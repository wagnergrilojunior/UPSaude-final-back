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

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(
    name = "extrato_bancario_importado",
    schema = "public",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_extrato_bancario_tenant_hash", columnNames = {"tenant_id", "hash_linha"})
    },
    indexes = {
        @Index(name = "idx_extrato_bancario_tenant_conta_data", columnList = "tenant_id, conta_financeira_id, data")
    }
)
@Data
@EqualsAndHashCode(callSuper = true)
public class ExtratoBancarioImportado extends BaseEntityFinanceiro {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conta_financeira_id", nullable = false)
    private ContaFinanceira contaFinanceira;

    @Column(name = "hash_linha", nullable = false, length = 64)
private String hashLinha;

    @Column(name = "descricao", length = 500)
    private String descricao;

    @Column(name = "valor", nullable = false, precision = 14, scale = 2)
    private BigDecimal valor;

    @Column(name = "data", nullable = false)
    private LocalDate data;

    @Column(name = "documento", length = 100)
    private String documento;

    @Column(name = "saldo_apos", precision = 14, scale = 2)
    private BigDecimal saldoApos;

    @Column(name = "status_conciliacao", nullable = false, length = 30)
    private String statusConciliacao = "NAO_CONCILIADO"; 
}
