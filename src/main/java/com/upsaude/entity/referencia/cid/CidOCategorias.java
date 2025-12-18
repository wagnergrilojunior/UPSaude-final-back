package com.upsaude.entity.referencia.cid;
import com.upsaude.entity.BaseEntityWithoutTenant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(
    name = "cid_o_categorias",
    schema = "public",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_cid_o_categorias_cat", columnNames = {"cat"})
    },
    indexes = {
        @Index(name = "idx_cid_o_categorias_cat", columnList = "cat")
    }
)
@Data
@EqualsAndHashCode(callSuper = true)
public class CidOCategorias extends BaseEntityWithoutTenant {

    // CSV: CAT;DESCRICAO;REFER;
    @Column(name = "cat", nullable = false, length = 20)
    private String cat;

    @Column(name = "descricao", nullable = false, columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "refer", columnDefinition = "TEXT")
    private String refer;
}

