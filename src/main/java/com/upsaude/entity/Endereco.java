package com.upsaude.entity;

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
import jakarta.validation.constraints.Size;
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

    @Column(name = "numero", length = 10)
    private String numero;

    @Column(name = "complemento", length = 100)
    private String complemento;

    @Column(name = "bairro", length = 100)
    private String bairro;

    @Column(name = "cep", length = 8)
    private String cep;

    @Column(name = "pais", length = 50)
    private String pais;

    @Column(name = "distrito", length = 100)
    private String distrito;

    @Column(name = "ponto_referencia", length = 255)
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

    @Column(name = "codigo_ibge_municipio", length = 7)
    private String codigoIbgeMunicipio;

    @Size(max = 10, message = "Microárea deve ter no máximo 10 caracteres")
    @Column(name = "microarea", length = 10)
    private String microarea;

    @Size(max = 15, message = "INE da equipe deve ter no máximo 15 caracteres")
    @Column(name = "ine_equipe", length = 15)
    private String ineEquipe;

    @Size(max = 20, message = "Quadra deve ter no máximo 20 caracteres")
    @Column(name = "quadra", length = 20)
    private String quadra;

    @Size(max = 20, message = "Lote deve ter no máximo 20 caracteres")
    @Column(name = "lote", length = 20)
    private String lote;

    @Size(max = 200, message = "Descrição da zona rural deve ter no máximo 200 caracteres")
    @Column(name = "zona_rural_descricao", length = 200)
    private String zonaRuralDescricao;

    @Size(max = 5, message = "Andar deve ter no máximo 5 caracteres")
    @Column(name = "andar", length = 5)
    private String andar;

    @Size(max = 20, message = "Bloco deve ter no máximo 20 caracteres")
    @Column(name = "bloco", length = 20)
    private String bloco;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estado_id")
    private Estados estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cidade_id")
    private Cidades cidade;
}
