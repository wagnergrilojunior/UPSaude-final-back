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
 * Servi?o do SIGTAP.
 */
@Entity
@Table(
        name = "sigtap_servico",
        schema = "public",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_sigtap_servico_codigo_oficial", columnNames = {"codigo_oficial"})
        },
        indexes = {
                @Index(name = "idx_sigtap_servico_nome", columnList = "nome")
        }
)
@Data
@EqualsAndHashCode(callSuper = true)
public class SigtapServico extends BaseEntityWithoutTenant {

    @Column(name = "codigo_oficial", nullable = false, length = 3)
    private String codigoOficial;

    @Column(name = "nome", length = 100)
    private String nome;
}
