package com.upsaude.entity.referencia.cid;
import com.upsaude.entity.BaseEntityWithoutTenant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
    name = "cid10_categorias",
    schema = "public",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_cid10_categorias_cat", columnNames = {"cat"})
    },
    indexes = {
        @Index(name = "idx_cid10_categorias_cat", columnList = "cat"),
        @Index(name = "idx_cid10_categorias_classif", columnList = "classif")
    }
)
@DynamicUpdate
@BatchSize(size = 50)
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"subcategorias"})
public class Cid10Categorias extends BaseEntityWithoutTenant {

    public Cid10Categorias() {
        this.subcategorias = new ArrayList<>();
    }

    @Column(name = "cat", nullable = false, length = 10)
    private String cat;

    @Column(name = "classif", length = 20)
    private String classif;

    @Column(name = "descricao", nullable = false, columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "descrabrev", columnDefinition = "TEXT")
    private String descrAbrev;

    @Column(name = "refer", columnDefinition = "TEXT")
    private String refer;

    @Column(name = "excluidos", columnDefinition = "TEXT")
    private String excluidos;

    @OneToMany(mappedBy = "categoria", fetch = FetchType.LAZY)
    private List<Cid10Subcategorias> subcategorias = new ArrayList<>();
}
