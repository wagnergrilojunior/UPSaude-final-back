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
        name = "sigtap_grupo_habilitacao",
        schema = "public",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_sigtap_grupo_habilitacao_codigo_oficial", columnNames = {"codigo_oficial"})
        },
        indexes = {
                @Index(name = "idx_sigtap_grupo_habilitacao_nome", columnList = "nome")
        }
)
@Data
@EqualsAndHashCode(callSuper = true)
public class SigtapGrupoHabilitacao extends BaseEntityWithoutTenant {

    @Column(name = "codigo_oficial", nullable = false, length = 4)
    private String codigoOficial;

    @Column(name = "nome", length = 20)
    private String nome;

    @Column(name = "descricao", length = 250)
    private String descricao;
}
