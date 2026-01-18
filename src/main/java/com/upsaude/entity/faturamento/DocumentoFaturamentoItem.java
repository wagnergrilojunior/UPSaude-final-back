package com.upsaude.entity.faturamento;

import com.upsaude.entity.BaseEntity;
import com.upsaude.entity.referencia.sigtap.SigtapProcedimento;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(
    name = "documento_faturamento_item",
    schema = "public",
    indexes = {
        @Index(name = "idx_documento_faturamento_item_documento", columnList = "documento_id")
    }
)
@Data
@EqualsAndHashCode(callSuper = true)
public class DocumentoFaturamentoItem extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "documento_id", nullable = false)
    private DocumentoFaturamento documento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sigtap_procedimento_id")
    private SigtapProcedimento sigtapProcedimento;

    @Column(name = "quantidade", nullable = false)
    private Integer quantidade = 1;

    @Column(name = "valor_unitario", precision = 14, scale = 2)
    private BigDecimal valorUnitario;

    @Column(name = "valor_total", precision = 14, scale = 2)
    private BigDecimal valorTotal;

    @Column(name = "origem_tipo", length = 50)
    private String origemTipo; 

    @Column(name = "origem_id")
    private UUID origemId;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "payload_layout_item", columnDefinition = "jsonb")
    private String payloadLayoutItem; 
}
