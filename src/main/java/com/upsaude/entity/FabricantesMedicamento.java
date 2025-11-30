package com.upsaude.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "fabricantes_medicamento", schema = "public")
@Data
@EqualsAndHashCode(callSuper = true)
public class FabricantesMedicamento extends BaseEntityWithoutTenant {

    @Column(name = "nome", nullable = false, length = 255)
    private String nome;

    @Column(name = "pais", length = 100)
    private String pais;

    @Column(name = "contato_json", columnDefinition = "jsonb")
    private String contatoJson;
}

