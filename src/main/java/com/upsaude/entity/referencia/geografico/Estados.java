package com.upsaude.entity.referencia.geografico;
import com.upsaude.entity.BaseEntityWithoutTenant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "estados", schema = "public")
@Data
@EqualsAndHashCode(callSuper = true)
public class Estados extends BaseEntityWithoutTenant {

    @Column(name = "sigla", length = 2, nullable = false)
    private String sigla;

    @Column(name = "nome", length = 100, nullable = false)
    private String nome;

    @Column(name = "codigo_ibge", length = 20)
    private String codigoIbge;

    @Column(name = "nome_oficial_ibge", length = 200)
    private String nomeOficialIbge;

    @Column(name = "sigla_ibge", length = 2)
    private String siglaIbge;

    @Column(name = "regiao_ibge", length = 50)
    private String regiaoIbge;

    @Column(name = "ativo_ibge")
    private Boolean ativoIbge;

    @Column(name = "data_ultima_sincronizacao_ibge")
    private java.time.OffsetDateTime dataUltimaSincronizacaoIbge;
}
