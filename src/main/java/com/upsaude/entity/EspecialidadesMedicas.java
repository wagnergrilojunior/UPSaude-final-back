package com.upsaude.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "especialidades_medicas", schema = "public")
@Data
public class EspecialidadesMedicas extends BaseEntity {

    @Column(name = "nome", nullable = false, length = 200)
    private String nome;

    @Column(name = "codigo", length = 50)
    private String codigo;

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;
}

