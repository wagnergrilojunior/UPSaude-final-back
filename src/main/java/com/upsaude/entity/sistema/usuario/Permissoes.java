package com.upsaude.entity.sistema.usuario;
import com.upsaude.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "permissoes", schema = "public")
@Data
@EqualsAndHashCode(callSuper = true)
public class Permissoes extends BaseEntity {

    @Column(name = "nome", nullable = false, length = 50)
    private String nome;

    @Column(name = "descricao", length = 200)
    private String descricao;

    @Column(name = "modulo", nullable = false, length = 50)
    private String modulo;
}
