package com.upsaude.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "medicacoes_continuas", schema = "public")
@Data
@EqualsAndHashCode(callSuper = true)
public class MedicacoesContinuas extends BaseEntityWithoutTenant {

    @Column(name = "nome", nullable = false, length = 255)
    private String nome;

    @Column(name = "dosagem", length = 100)
    private String dosagem;

    @Column(name = "fabricante", length = 255)
    private String fabricante;
}
