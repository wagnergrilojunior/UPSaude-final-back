package com.upsaude.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "instituicoes")
@Data
@EqualsAndHashCode(callSuper = true)
public class Instituicao extends BaseEntity {

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "cnpj", nullable = false, unique = true, length = 14)
    private String cnpj;

    @Column(name = "tipo", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoInstituicao tipo;

    @Column(name = "telefone", length = 20)
    private String telefone;

    @Column(name = "email", length = 100)
    private String email;

    @Embedded
    private Endereco endereco;

    @Column(name = "schema_name", nullable = false, unique = true)
    private String schemaName;

    @OneToMany(mappedBy = "instituicao", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Usuario> usuarios = new HashSet<>();

    public enum TipoInstituicao {
        PUBLICA,
        PRIVADA,
        CONVENIADA
    }

}