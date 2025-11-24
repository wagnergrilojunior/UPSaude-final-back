package com.upsaude.entity;

import com.upsaude.enums.TipoLogradouroEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "enderecos", schema = "public")
@Data
public class Endereco extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_logradouro", length = 20)
    private TipoLogradouroEnum tipoLogradouro;

    @Column(name = "logradouro", length = 200)
    private String logradouro;

    @Column(name = "numero", length = 10)
    private String numero;

    @Column(name = "complemento", length = 100)
    private String complemento;

    @Column(name = "bairro", length = 100)
    private String bairro;

    @Column(name = "cidade", length = 100)
    private String cidade;

    @Column(name = "estado", length = 2)
    private String estado;

    @Column(name = "cep", length = 8)
    private String cep;

    @Column(name = "pais", length = 50)
    private String pais;

    @Column(name = "codigo_ibge", length = 10)
    private String codigoIbge;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estado_id")
    private Estados estadoRef;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cidade_id")
    private Cidades cidadeRef;
}
