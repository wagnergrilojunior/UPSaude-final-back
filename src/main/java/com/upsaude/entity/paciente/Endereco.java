package com.upsaude.entity.paciente;

import com.upsaude.entity.BaseEntity;
import com.upsaude.entity.referencia.geografico.Estados;
import com.upsaude.entity.referencia.geografico.Cidades;

import com.upsaude.enums.TipoEnderecoEnum;
import com.upsaude.enums.TipoLogradouroEnum;
import com.upsaude.enums.ZonaDomicilioEnum;
import com.upsaude.util.converter.TipoEnderecoEnumConverter;
import com.upsaude.util.converter.TipoLogradouroEnumConverter;
import com.upsaude.util.converter.ZonaDomicilioEnumConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "enderecos", schema = "public")
@Data
@EqualsAndHashCode(callSuper = true)
public class Endereco extends BaseEntity {

    @Convert(converter = TipoLogradouroEnumConverter.class)
    @Column(name = "tipo_logradouro")
    private TipoLogradouroEnum tipoLogradouro;

    @Column(name = "logradouro", length = 200)
    private String logradouro;

    @Column(name = "numero", length = 50)
    private String numero;

    @Column(name = "complemento", length = 150)
    private String complemento;

    @Column(name = "bairro", length = 200)
    private String bairro;

    @Column(name = "cep", length = 20)
    private String cep;

    @Column(name = "pais", length = 100)
    private String pais;

    @Column(name = "distrito", length = 100)
    private String distrito;

    @Column(name = "ponto_referencia", length = 300)
    private String pontoReferencia;

    @Column(name = "sem_numero", nullable = false)
    private Boolean semNumero = false;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Convert(converter = TipoEnderecoEnumConverter.class)
    @Column(name = "tipo_endereco")
    private TipoEnderecoEnum tipoEndereco;

    @Convert(converter = ZonaDomicilioEnumConverter.class)
    @Column(name = "zona")
    private ZonaDomicilioEnum zona;

    @Column(name = "codigo_ibge_municipio", length = 20)
    private String codigoIbgeMunicipio;

    @Column(name = "quadra", length = 50)
    private String quadra;

    @Column(name = "lote", length = 50)
    private String lote;

    @Column(name = "zona_rural_descricao", length = 200)
    private String zonaRuralDescricao;

    @Column(name = "andar", length = 50)
    private String andar;

    @Column(name = "bloco", length = 50)
    private String bloco;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estado_id")
    private Estados estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cidade_id")
    private Cidades cidade;
}
