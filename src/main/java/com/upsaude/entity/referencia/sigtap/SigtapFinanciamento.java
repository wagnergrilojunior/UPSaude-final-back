package com.upsaude.entity.referencia.sigtap;

import com.upsaude.entity.BaseEntityWithoutTenant;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Financiamento do SIGTAP.
 */
@Entity
@Table(
        name = "sigtap_financiamento",
        schema = "public",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_sigtap_financiamento_codigo_comp", columnNames = {"codigo_oficial", "competencia_inicial"})
        },
        indexes = {
                @Index(name = "idx_sigtap_financiamento_nome", columnList = "nome")
        }
)
@Data
@EqualsAndHashCode(callSuper = true)
public class SigtapFinanciamento extends BaseEntityWithoutTenant {

    @Column(name = "codigo_oficial", nullable = false, length = 2)
    private String codigoOficial;

    @Column(name = "nome", length = 100)
    private String nome;

    @Column(name = "competencia_inicial", length = 6)
    private String competenciaInicial;

    @Column(name = "competencia_final", length = 6)
    private String competenciaFinal;
}
