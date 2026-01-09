package com.upsaude.entity.referencia.sigtap;

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
        name = "sigtap_rede_atencao",
        schema = "public",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_sigtap_rede_atencao_codigo_oficial", columnNames = {"codigo_oficial"})
        },
        indexes = {
                @Index(name = "idx_sigtap_rede_atencao_nome", columnList = "nome")
        }
)
@Data
@EqualsAndHashCode(callSuper = true)
public class SigtapRedeAtencao extends BaseEntityWithoutTenant {

    @Column(name = "codigo_oficial", nullable = false, length = 3)
    private String codigoOficial;

    @Column(name = "nome", length = 50)
    private String nome;
}
