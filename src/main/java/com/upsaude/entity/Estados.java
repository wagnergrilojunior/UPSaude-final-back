package com.upsaude.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Entidade que representa um Estado.
 * Não possui relação com Tenant por ser uma entidade de referência.
 *
 * @author UPSaúde
 */
@Entity
@Table(name = "estados", schema = "public")
@Data
@EqualsAndHashCode(callSuper = true)
public class Estados extends BaseEntityWithoutTenant {

    @Column(name = "sigla", length = 2, nullable = false)
    private String sigla;

    @Column(name = "nome", length = 100, nullable = false)
    private String nome;

    @Column(name = "codigo_ibge", length = 10)
    private String codigoIbge;
}

