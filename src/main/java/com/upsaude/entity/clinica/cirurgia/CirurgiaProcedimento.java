package com.upsaude.entity.clinica.cirurgia;

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

import java.math.BigDecimal;

@Entity
@Table(name = "cirurgia_procedimento", schema = "public",
       indexes = {
           @Index(name = "idx_cirurgia_procedimento_cirurgia", columnList = "cirurgia_id"),
           @Index(name = "idx_cirurgia_procedimento_procedimento", columnList = "procedimento_id")
       })
@Data
@EqualsAndHashCode(callSuper = true)
public class CirurgiaProcedimento extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cirurgia_id", nullable = false)
    private Cirurgia cirurgia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "procedimento_id", nullable = false)
    private SigtapProcedimento procedimento;

    @Column(name = "quantidade", nullable = false)
    private Integer quantidade = 1;

    @Column(name = "valor_unitario", precision = 14, scale = 2)
    private BigDecimal valorUnitario;

    @Column(name = "valor_total", precision = 14, scale = 2)
    private BigDecimal valorTotal;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;
}

