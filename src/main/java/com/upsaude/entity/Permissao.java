package com.upsaude.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "permissoes")
@Data
@EqualsAndHashCode(callSuper = true)
public class Permissao extends BaseEntity {

    @Column(name = "nome", nullable = false, unique = true, length = 50)
    private String nome;

    @Column(name = "descricao", length = 200)
    private String descricao;

    @Column(name = "modulo", nullable = false, length = 50)
    private String modulo;

}