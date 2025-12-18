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
 * CID (Classifica??o Internacional de Doen?as) do SIGTAP.
 */
@Entity
@Table(
        name = "sigtap_cid",
        schema = "public",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_sigtap_cid_codigo_oficial", columnNames = {"codigo_oficial"})
        },
        indexes = {
                @Index(name = "idx_sigtap_cid_nome", columnList = "nome")
        }
)
@Data
@EqualsAndHashCode(callSuper = true)
public class SigtapCid extends BaseEntityWithoutTenant {

    @Column(name = "codigo_oficial", nullable = false, length = 4)
    private String codigoOficial;

    @Column(name = "nome", length = 100)
    private String nome;

    @Column(name = "tipo_agravo", length = 1)
    private String tipoAgravo;

    @Column(name = "tipo_sexo", length = 1)
    private String tipoSexo;

    @Column(name = "tipo_estadio", length = 1)
    private String tipoEstadio;

    @Column(name = "valor_campos_irradiados")
    private Integer valorCamposIrradiados;
}
