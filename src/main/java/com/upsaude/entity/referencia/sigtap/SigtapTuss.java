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
 * TUSS do SIGTAP.
 */
@Entity
@Table(
        name = "sigtap_tuss",
        schema = "public",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_sigtap_tuss_codigo_oficial", columnNames = {"codigo_oficial"})
        },
        indexes = {
                @Index(name = "idx_sigtap_tuss_nome", columnList = "nome")
        }
)
@Data
@EqualsAndHashCode(callSuper = true)
public class SigtapTuss extends BaseEntityWithoutTenant {

    @Column(name = "codigo_oficial", nullable = false, length = 10)
    private String codigoOficial;

    @Column(name = "nome", length = 300)
    private String nome;
}
