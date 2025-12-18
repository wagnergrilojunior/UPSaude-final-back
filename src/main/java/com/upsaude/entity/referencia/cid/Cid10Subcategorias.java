package com.upsaude.entity.referencia.cid;
import com.upsaude.entity.BaseEntityWithoutTenant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.DynamicUpdate;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(
    name = "cid10_subcategorias",
    schema = "public",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_cid10_subcategorias_subcat", columnNames = {"subcat"})
    },
    indexes = {
        @Index(name = "idx_cid10_subcategorias_subcat", columnList = "subcat"),
        @Index(name = "idx_cid10_subcategorias_categoria_cat", columnList = "categoria_cat"),
        @Index(name = "idx_cid10_subcategorias_classif", columnList = "classif"),
        @Index(name = "idx_cid10_subcategorias_restrsexo", columnList = "restrsexo"),
        @Index(name = "idx_cid10_subcategorias_causaobito", columnList = "causaobito")
    }
)
@DynamicUpdate
@BatchSize(size = 50)
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"categoria"})
public class Cid10Subcategorias extends BaseEntityWithoutTenant {

    @Column(name = "subcat", nullable = false, length = 10)
    private String subcat;

    @Column(name = "categoria_cat", length = 10)
    private String categoriaCat;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "categoria_cat", referencedColumnName = "cat", insertable = false, updatable = false)
    private Cid10Categorias categoria;

    @Column(name = "classif", length = 20)
    private String classif;

    @Column(name = "restrsexo", length = 5)
    private String restrSexo;

    @Column(name = "causaobito", length = 5)
    private String causaObito;

    @Column(name = "descricao", nullable = false, columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "descrabrev", columnDefinition = "TEXT")
    private String descrAbrev;

    @Column(name = "refer", columnDefinition = "TEXT")
    private String refer;

    @Column(name = "excluidos", columnDefinition = "TEXT")
    private String excluidos;

    @PrePersist
    @PreUpdate
    private void preencherCategoriaCat() {
        if ((categoriaCat == null || categoriaCat.isBlank()) && subcat != null && subcat.length() >= 3) {
            this.categoriaCat = subcat.substring(0, 3);
        }
    }
}

