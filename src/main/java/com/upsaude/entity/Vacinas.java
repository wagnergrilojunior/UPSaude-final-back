package com.upsaude.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "vacinas", schema = "public")
@Data
public class Vacinas extends BaseEntity { 

    @Column(name = "nome", nullable = false, length = 255)
    private String nome;

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "fabricante", length = 255)
    private String fabricante;

    @Column(name = "lote", length = 100)
    private String lote;

    @Column(name = "data_validade")
    private java.time.LocalDate dataValidade;

    @Column(name = "dose_ml")
    private java.math.BigDecimal doseMl;

    
}
