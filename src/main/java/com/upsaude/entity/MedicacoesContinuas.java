package com.upsaude.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "medicacoes_continuas", schema = "public")
@Data
public class MedicacoesContinuas extends BaseEntity {

    @Column(name = "nome", nullable = false, length = 255)
    private String nome;

    @Column(name = "dosagem", length = 100)
    private String dosagem;

    @Column(name = "fabricante", length = 255)
    private String fabricante;
}
