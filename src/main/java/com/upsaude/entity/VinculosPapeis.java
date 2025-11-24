package com.upsaude.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "vinculos_papeis", schema = "public")
@Data
public class VinculosPapeis extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estabelecimento_id")
    private Estabelecimentos estabelecimento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departamento_id")
    private Departamentos departamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "papel_id", nullable = false)
    private Papeis papel;
}
