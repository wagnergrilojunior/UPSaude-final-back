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
    name = "centro_custo",
    schema = "public",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_centro_custo_tenant_codigo", columnNames = {"tenant_id", "codigo"})
    },
    indexes = {
        @Index(name = "idx_centro_custo_pai", columnList = "tenant_id, pai_id")
    }
)
@Data
@EqualsAndHashCode(callSuper = true)
public class CentroCusto extends BaseEntityFinanceiro {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pai_id")
    private CentroCusto pai;

    @Column(name = "codigo", nullable = false, length = 50)
    private String codigo;

    @Column(name = "nome", nullable = false, length = 255)
    private String nome;

    @Column(name = "ordem")
    private Integer ordem;
}
