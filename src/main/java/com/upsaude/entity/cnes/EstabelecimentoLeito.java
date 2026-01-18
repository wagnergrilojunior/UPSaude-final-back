package com.upsaude.entity.cnes;

import com.upsaude.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Entity
@Table(name = "estabelecimento_leitos", schema = "public")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class EstabelecimentoLeito extends BaseEntity {

    
    

    @Column(name = "tipo_leito_codigo", nullable = false, length = 10)
    private String tipoLeitoCodigo;

    @Column(name = "tipo_leito_descricao", length = 255)
    private String tipoLeitoDescricao;

    @Column(name = "quantidade_existente")
    private Integer quantidadeExistente;

    @Column(name = "quantidade_contratada")
    private Integer quantidadeContratada;

    @Column(name = "quantidade_sus")
    private Integer quantidadeSus;

    @Column(name = "data_atualizacao")
    private OffsetDateTime dataAtualizacao;

}
