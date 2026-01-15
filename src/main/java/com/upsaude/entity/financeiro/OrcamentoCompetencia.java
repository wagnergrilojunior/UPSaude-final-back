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

@Entity
@Table(
    name = "orcamento_competencia",
    schema = "public",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_orcamento_competencia_tenant_competencia", columnNames = {"tenant_id", "competencia_id"})
    },
    indexes = {
        @Index(name = "idx_orcamento_competencia_competencia", columnList = "tenant_id, competencia_id")
    }
)
@Data
@EqualsAndHashCode(callSuper = true)
public class OrcamentoCompetencia extends BaseEntityFinanceiro {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "competencia_id", nullable = false)
    private CompetenciaFinanceira competencia;

    @Column(name = "saldo_anterior", precision = 14, scale = 2)
    private BigDecimal saldoAnterior;

    @Column(name = "creditos", precision = 14, scale = 2)
    private BigDecimal creditos;

    @Column(name = "reservas_ativas", precision = 14, scale = 2)
    private BigDecimal reservasAtivas;

    @Column(name = "consumos", precision = 14, scale = 2)
    private BigDecimal consumos;

    @Column(name = "estornos", precision = 14, scale = 2)
    private BigDecimal estornos;

    @Column(name = "despesas_admin", precision = 14, scale = 2)
    private BigDecimal despesasAdmin;

    @Column(name = "saldo_final", precision = 14, scale = 2)
    private BigDecimal saldoFinal;

    
    public BigDecimal calcularSaldoDisponivel() {
        BigDecimal saldo = saldoAnterior != null ? saldoAnterior : BigDecimal.ZERO;
        saldo = saldo.add(creditos != null ? creditos : BigDecimal.ZERO);
        saldo = saldo.subtract(reservasAtivas != null ? reservasAtivas : BigDecimal.ZERO);
        saldo = saldo.subtract(consumos != null ? consumos : BigDecimal.ZERO);
        saldo = saldo.add(estornos != null ? estornos : BigDecimal.ZERO);
        saldo = saldo.subtract(despesasAdmin != null ? despesasAdmin : BigDecimal.ZERO);
        return saldo;
    }

    
    public boolean temSaldoDisponivel(BigDecimal valorRequerido) {
        if (valorRequerido == null || valorRequerido.compareTo(BigDecimal.ZERO) <= 0) {
            return true;
        }
        return calcularSaldoDisponivel().compareTo(valorRequerido) >= 0;
    }
}
