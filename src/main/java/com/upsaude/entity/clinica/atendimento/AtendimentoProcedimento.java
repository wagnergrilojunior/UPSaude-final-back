package com.upsaude.entity.clinica.atendimento;

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
import java.util.UUID;

@Entity
@Table(
    name = "atendimento_procedimento",
    schema = "public",
    indexes = {
        @Index(name = "idx_atendimento_procedimento_atendimento", columnList = "atendimento_id"),
        @Index(name = "idx_atendimento_procedimento_procedimento", columnList = "sigtap_procedimento_id")
    }
)
@Data
@EqualsAndHashCode(callSuper = true)
public class AtendimentoProcedimento extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "atendimento_id", nullable = false)
    private Atendimento atendimento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sigtap_procedimento_id")
    private SigtapProcedimento sigtapProcedimento;

    @Column(name = "quantidade", nullable = false)
    private Integer quantidade = 1;

    @Column(name = "valor_unitario", precision = 14, scale = 2)
    private BigDecimal valorUnitario;

    @Column(name = "valor_total", precision = 14, scale = 2)
    private BigDecimal valorTotal;

    // Snapshots de classificação (opcional, mas vendável)
    @Column(name = "financiamento_id")
    private UUID financiamentoId;

    @Column(name = "rubrica_id")
    private UUID rubricaId;

    @Column(name = "modalidade_financeira", length = 50)
    private String modalidadeFinanceira;

    // Campos mínimos para layout (opcional)
    @Column(name = "cbo_codigo", length = 6)
    private String cboCodigo;

    @Column(name = "cid_principal_codigo", length = 10)
    private String cidPrincipalCodigo;

    @Column(name = "carater_atendimento", length = 2)
    private String caraterAtendimento;

    @Column(name = "cnes", length = 7)
    private String cnes;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;
}
