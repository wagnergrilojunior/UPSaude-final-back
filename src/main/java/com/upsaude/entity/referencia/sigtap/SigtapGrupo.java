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
        name = "sigtap_grupo",
        schema = "public",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_sigtap_grupo_codigo_oficial", columnNames = {"codigo_oficial"})
        },
        indexes = {
                @Index(name = "idx_sigtap_grupo_nome", columnList = "nome")
        }
)
@Data
@EqualsAndHashCode(callSuper = true)
public class SigtapGrupo extends BaseEntityWithoutTenant {

    @Column(name = "codigo_oficial", nullable = false, length = 20)
    private String codigoOficial;

    @Column(name = "nome", length = 255)
    private String nome;

    @Column(name = "competencia_inicial", length = 6)
    private String competenciaInicial;

    @Column(name = "competencia_final", length = 6)
    private String competenciaFinal;
}
