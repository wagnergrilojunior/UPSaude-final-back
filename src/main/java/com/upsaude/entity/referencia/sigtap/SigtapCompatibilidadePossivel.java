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
 * Compatibilidade Poss?vel (cat?logo) no SIGTAP.
 */
@Entity
@Table(
        name = "sigtap_compatibilidade_possivel",
        schema = "public",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_sigtap_comp_possivel_codigo", columnNames = {"codigo_oficial"})
        },
        indexes = {
                @Index(name = "idx_sigtap_comp_possivel_nome", columnList = "nome")
        }
)
@Data
@EqualsAndHashCode(callSuper = true)
public class SigtapCompatibilidadePossivel extends BaseEntityWithoutTenant {

    @Column(name = "codigo_oficial", nullable = false, length = 20)
    private String codigoOficial;

    @Column(name = "nome", length = 255)
    private String nome;

    @Column(name = "competencia_inicial", length = 6)
    private String competenciaInicial;

    @Column(name = "competencia_final", length = 6)
    private String competenciaFinal;

    @Column(name = "tipo_compatibilidade", length = 30)
    private String tipoCompatibilidade;

    @Column(name = "instrumento_registro_principal", columnDefinition = "jsonb")
    private String instrumentoRegistroPrincipal;

    @Column(name = "instrumento_registro_secundario", columnDefinition = "jsonb")
    private String instrumentoRegistroSecundario;
}

