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

    @Column(name = "nome_oficial_ibge", length = 200)
    private String nomeOficialIbge;

    @Column(name = "uf_ibge", length = 2)
    private String ufIbge;

    @Column(name = "populacao_estimada")
    private Integer populacaoEstimada;

    @Column(name = "ativo_ibge")
    private Boolean ativoIbge;

    @Column(name = "data_ultima_sincronizacao_ibge")
    private java.time.OffsetDateTime dataUltimaSincronizacaoIbge;

    
    @Column(name = "codigo_fhir", length = 20)
    private String codigoFhir;

    @Column(name = "fhir_code_system", length = 200)
    private String fhirCodeSystem;

    @Column(name = "regiao_saude", length = 100)
    private String regiaoSaude;

    @Column(name = "macrorregiao_saude", length = 100)
    private String macrorregiaoSaude;

    @Column(name = "data_ultima_sincronizacao_fhir")
    private java.time.OffsetDateTime dataUltimaSincronizacaoFhir;
}
