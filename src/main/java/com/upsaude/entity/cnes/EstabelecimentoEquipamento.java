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
@Table(name = "estabelecimento_equipamentos", schema = "public")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class EstabelecimentoEquipamento extends BaseEntity {

    
    

    @Column(name = "tipo_equipamento_codigo", nullable = false, length = 10)
    private String tipoEquipamentoCodigo;

    @Column(name = "tipo_equipamento_descricao", length = 255)
    private String tipoEquipamentoDescricao;

    @Column(name = "quantidade_existente")
    private Integer quantidadeExistente;

    @Column(name = "quantidade_em_uso")
    private Integer quantidadeEmUso;

    @Column(name = "quantidade_sus")
    private Integer quantidadeSus;

    @Column(name = "data_atualizacao")
    private OffsetDateTime dataAtualizacao;

}
