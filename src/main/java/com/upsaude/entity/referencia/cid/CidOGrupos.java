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
    name = "cid_o_grupos",
    schema = "public",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_cid_o_grupos_intervalo", columnNames = {"catinic", "catfim"})
    },
    indexes = {
        @Index(name = "idx_cid_o_grupos_catinic", columnList = "catinic"),
        @Index(name = "idx_cid_o_grupos_catfim", columnList = "catfim")
    }
)
@Data
@EqualsAndHashCode(callSuper = true)
public class CidOGrupos extends BaseEntityWithoutTenant {

    // CSV: CATINIC;CATFIM;DESCRICAO;REFER;
    @Column(name = "catinic", nullable = false, length = 20)
    private String catinic;

    @Column(name = "catfim", nullable = false, length = 20)
    private String catfim;

    @Column(name = "descricao", nullable = false, columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "refer", columnDefinition = "TEXT")
    private String refer;
}

