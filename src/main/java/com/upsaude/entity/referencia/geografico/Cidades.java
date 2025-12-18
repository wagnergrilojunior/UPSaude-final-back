package com.upsaude.entity.referencia.geografico;
import com.upsaude.entity.referencia.geografico.Estados;
import com.upsaude.entity.BaseEntityWithoutTenant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "cidades", schema = "public")
@Data
@EqualsAndHashCode(callSuper = true)
public class Cidades extends BaseEntityWithoutTenant {

    @Column(name = "nome", length = 200, nullable = false)
    private String nome;

    @Column(name = "codigo_ibge", length = 10)
    private String codigoIbge;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estado_id", nullable = false)
    private Estados estado;
}
