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
    name = "cid10_grupos",
    schema = "public",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_cidP10_grupos_intervalo", columnNames = {"catinic", "catfim"})
    },
    indexes = {
        @Index(name = "idx_cid10_grupos_catinic", columnList = "catinic"),
        @Index(name = "idx_cid10_grupos_catfim", columnList = "catfim")
    }
)
@Data
@EqualsAndHashCode(callSuper = true)
public class Cid10Grupos extends BaseEntityWithoutTenant {

    @Column(name = "catinic", nullable = false, length = 10)
    private String catinic;

    @Column(name = "catfim", nullable = false, length = 10)
    private String catfim;

    @Column(name = "descricao", nullable = false, columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "descricao_abreviada", columnDefinition = "TEXT")
    private String descricaoAbreviada;
}
