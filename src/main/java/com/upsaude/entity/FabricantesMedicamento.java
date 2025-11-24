package com.upsaude.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "fabricantes_medicamento", schema = "public")
@Data
public class FabricantesMedicamento extends BaseEntity {

    @Column(name = "nome", nullable = false, length = 255)
    private String nome;

    @Column(name = "pais", length = 100)
    private String pais;

    @Column(name = "contato_json", columnDefinition = "jsonb")
    private String contatoJson;
}

