package com.upsaude.entity.financeiro;

import com.upsaude.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Entity
@Table(
    name = "parte_financeira",
    schema = "public",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_parte_financeira_tenant_tipo_doc", columnNames = {"tenant_id", "tipo", "documento"})
    },
    indexes = {
        @Index(name = "idx_parte_financeira_tipo", columnList = "tenant_id, tipo")
    }
)
@Data
@EqualsAndHashCode(callSuper = true)
public class ParteFinanceira extends BaseEntity {

    @Column(name = "tipo", nullable = false, length = 30)
    private String tipo; // PACIENTE | CONVENIO | ORGAO_PUBLICO | EMPRESA | PESSOA_FISICA | OUTRO

    @Column(name = "nome", nullable = false, length = 255)
    private String nome;

    @Column(name = "documento", length = 20)
    private String documento; // CPF/CNPJ

    @Column(name = "email", length = 255)
    private String email;

    @Column(name = "telefone", length = 50)
    private String telefone;

    @Column(name = "referencia_tipo", length = 50)
    private String referenciaTipo; // Para linkar com entidades existentes

    @Column(name = "referencia_id")
    private UUID referenciaId;
}
