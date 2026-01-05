package com.upsaude.entity.farmacia;

import com.upsaude.entity.BaseEntity;
import com.upsaude.entity.referencia.sigtap.SigtapProcedimento;
import com.upsaude.enums.UnidadeMedidaEnum;
import com.upsaude.util.converter.UnidadeMedidaEnumConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "receita_itens", schema = "public",
       uniqueConstraints = {
           @UniqueConstraint(name = "uk_receita_item_receita_posicao", columnNames = {"receita_id", "posicao"})
       },
       indexes = {
           @Index(name = "idx_receita_item_receita", columnList = "receita_id"),
           @Index(name = "idx_receita_item_procedimento", columnList = "sigtap_procedimento_id"),
           @Index(name = "idx_receita_item_posicao", columnList = "receita_id,posicao")
       })
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class ReceitaItem extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receita_id", nullable = false)
    private Receita receita;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sigtap_procedimento_id", nullable = false)
    private SigtapProcedimento sigtapProcedimento;

    @OneToMany(mappedBy = "receitaItem", fetch = FetchType.LAZY)
    private List<DispensacaoItem> dispensacoes = new ArrayList<>();

    @Column(name = "posicao", nullable = false)
    private Integer posicao;

    @Column(name = "quantidade_prescrita", precision = 10, scale = 3)
    private BigDecimal quantidadePrescrita;

    @Convert(converter = UnidadeMedidaEnumConverter.class)
    @Column(name = "unidade_medida")
    private UnidadeMedidaEnum unidadeMedida;

    @Column(name = "posologia", length = 500)
    private String posologia;

    @Column(name = "duracao_tratamento")
    private Integer duracaoTratamento;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;
}

