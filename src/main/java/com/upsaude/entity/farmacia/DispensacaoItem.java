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
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "dispensacao_itens", schema = "public", indexes = {
        @Index(name = "idx_dispensacao_item_dispensacao", columnList = "dispensacao_id"),
        @Index(name = "idx_dispensacao_item_receita_item", columnList = "receita_item_id"),
        @Index(name = "idx_dispensacao_item_procedimento", columnList = "sigtap_procedimento_id")
})
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class DispensacaoItem extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dispensacao_id", nullable = false)
    private Dispensacao dispensacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receita_item_id")
    private ReceitaItem receitaItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sigtap_procedimento_id", nullable = false)
    private SigtapProcedimento sigtapProcedimento;

    @Column(name = "quantidade_dispensada", precision = 10, scale = 3, nullable = false)
    private BigDecimal quantidadeDispensada;

    @Convert(converter = UnidadeMedidaEnumConverter.class)
    @Column(name = "unidade_medida")
    private UnidadeMedidaEnum unidadeMedida;

    @Column(name = "lote", length = 50)
    private String lote;

    @Column(name = "validade_lote")
    private LocalDate validadeLote;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;
}

