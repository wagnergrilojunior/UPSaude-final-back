package com.upsaude.entity.financeiro;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(
    name = "conta_financeira",
    schema = "public",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_conta_financeira_tenant_tipo_nome", columnNames = {"tenant_id", "tipo", "nome"})
    },
    indexes = {
        @Index(name = "idx_conta_financeira_tipo", columnList = "tenant_id, tipo")
    }
)
@Data
@EqualsAndHashCode(callSuper = true)
public class ContaFinanceira extends BaseEntityFinanceiro {

    @Column(name = "tipo", nullable = false, length = 20)
private String tipo;

    @Column(name = "nome", nullable = false, length = 255)
    private String nome;

    @Column(name = "moeda", nullable = false, length = 3)
    private String moeda = "BRL";

    @Column(name = "banco_codigo", length = 10)
    private String bancoCodigo;

    @Column(name = "agencia", length = 20)
    private String agencia;

    @Column(name = "numero_conta", length = 50)
    private String numeroConta;

    @Column(name = "pix_chave", length = 255)
    private String pixChave;
}
