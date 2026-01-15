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
    name = "plano_contas",
    schema = "public",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_plano_contas_tenant_nome_versao", columnNames = {"tenant_id", "nome", "versao"})
    },
    indexes = {
        @Index(name = "idx_plano_contas_tenant", columnList = "tenant_id"),
        @Index(name = "idx_plano_contas_padrao", columnList = "tenant_id, padrao")
    }
)
@Data
@EqualsAndHashCode(callSuper = true)
public class PlanoContas extends BaseEntityFinanceiro {

    @Column(name = "nome", nullable = false, length = 255)
    private String nome;

    @Column(name = "versao", nullable = false, length = 50)
    private String versao;

    @Column(name = "padrao", nullable = false)
    private Boolean padrao = false;
}
