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

/**
 * Forma de Organiza??o SIGTAP (3? n?vel de agrega??o).
 */
@Entity
@Table(
        name = "sigtap_forma_organizacao",
        schema = "public",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_sigtap_forma_org_subgrupo_codigo", columnNames = {"subgrupo_id", "codigo_oficial"})
        },
        indexes = {
                @Index(name = "idx_sigtap_forma_org_subgrupo_id", columnList = "subgrupo_id"),
                @Index(name = "idx_sigtap_forma_org_nome", columnList = "nome")
        }
)
@Data
@EqualsAndHashCode(callSuper = true)
public class SigtapFormaOrganizacao extends BaseEntityWithoutTenant {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "subgrupo_id", nullable = false)
    private SigtapSubgrupo subgrupo;

    @Column(name = "codigo_oficial", nullable = false, length = 20)
    private String codigoOficial;

    @Column(name = "nome", length = 255)
    private String nome;

    @Column(name = "competencia_inicial", length = 6)
    private String competenciaInicial;

    @Column(name = "competencia_final", length = 6)
    private String competenciaFinal;
}

