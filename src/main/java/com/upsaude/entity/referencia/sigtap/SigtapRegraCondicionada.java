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
 * Regra Condicionada do SIGTAP.
 */
@Entity
@Table(
        name = "sigtap_regra_condicionada",
        schema = "public",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_sigtap_regra_condicionada_codigo_oficial", columnNames = {"codigo_oficial"})
        },
        indexes = {
                @Index(name = "idx_sigtap_regra_condicionada_nome", columnList = "nome")
        }
)
@Data
@EqualsAndHashCode(callSuper = true)
public class SigtapRegraCondicionada extends BaseEntityWithoutTenant {

    @Column(name = "codigo_oficial", nullable = false, length = 4)
    private String codigoOficial;

    @Column(name = "nome", length = 300)
    private String nome;
}
