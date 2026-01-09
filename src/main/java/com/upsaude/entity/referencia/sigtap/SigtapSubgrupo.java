package com.upsaude.entity.referencia.sigtap;

import com.upsaude.entity.BaseEntityWithoutTenant;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(
        name = "sigtap_subgrupo",
        schema = "public",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_sigtap_subgrupo_grupo_codigo", columnNames = {"grupo_id", "codigo_oficial"})
        },
        indexes = {
                @Index(name = "idx_sigtap_subgrupo_grupo_id", columnList = "grupo_id"),
                @Index(name = "idx_sigtap_subgrupo_nome", columnList = "nome")
        }
)
@Data
@EqualsAndHashCode(callSuper = true)
public class SigtapSubgrupo extends BaseEntityWithoutTenant {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "grupo_id", nullable = false)
    private SigtapGrupo grupo;

    @Column(name = "codigo_oficial", nullable = false, length = 20)
    private String codigoOficial;

    @Column(name = "nome", length = 255)
    private String nome;

    @Column(name = "competencia_inicial", length = 6)
    private String competenciaInicial;

    @Column(name = "competencia_final", length = 6)
    private String competenciaFinal;
}
