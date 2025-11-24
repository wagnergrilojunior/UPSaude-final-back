package com.upsaude.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "estados", schema = "public")
@Data
public class Estados  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "sigla", length = 2, nullable = false)
    private String sigla;

    @Column(name = "nome", length = 100, nullable = false)
    private String nome;

    @Column(name = "codigo_ibge", length = 10)
    private String codigoIbge;
}

