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
    name = "cid10_capitulos",
    schema = "public",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_cid10_capitulos_numcap", columnNames = {"numcap"})
    },
    indexes = {
        @Index(name = "idx_cid10_capitulos_numcap", columnList = "numcap"),
        @Index(name = "idx_cid10_capitulos_catinic", columnList = "catinic"),
        @Index(name = "idx_cid10_capitulos_catfim", columnList = "catfim")
    }
)
@Data
@EqualsAndHashCode(callSuper = true)
public class Cid10Capitulos extends BaseEntityWithoutTenant {

    @Column(name = "numcap", nullable = false)
    private Integer numcap;

    @Column(name = "catinic", nullable = false, length = 10)
    private String catinic;

    @Column(name = "catfim", nullable = false, length = 10)
    private String catfim;

    @Column(name = "descricao", nullable = false, columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "descricao_abreviada", columnDefinition = "TEXT")
    private String descricaoAbreviada;
}
