package com.upsaude.entity.referencia.sigtap;

import com.upsaude.entity.BaseEntityWithoutTenant;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;

@Entity
@Table(
        name = "sigtap_procedimento_incremento",
        schema = "public",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_sigtap_proc_inc_proc_hab_comp", columnNames = {"procedimento_id", "habilitacao_id", "competencia_inicial"})
        },
        indexes = {
                @Index(name = "idx_sigtap_proc_inc_procedimento_id", columnList = "procedimento_id"),
                @Index(name = "idx_sigtap_proc_inc_habilitacao_id", columnList = "habilitacao_id")
        }
)
@Data
@EqualsAndHashCode(callSuper = true)
public class SigtapProcedimentoIncremento extends BaseEntityWithoutTenant {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "procedimento_id", nullable = false)
    private SigtapProcedimento procedimento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "habilitacao_id", nullable = false)
    private SigtapHabilitacao habilitacao;

    @Column(name = "valor_percentual_sh", precision = 7, scale = 2)
    private BigDecimal valorPercentualSh;

    @Column(name = "valor_percentual_sa", precision = 7, scale = 2)
    private BigDecimal valorPercentualSa;

    @Column(name = "valor_percentual_sp", precision = 7, scale = 2)
    private BigDecimal valorPercentualSp;

    @Column(name = "competencia_inicial", length = 6)
    private String competenciaInicial;

    @Column(name = "competencia_final", length = 6)
    private String competenciaFinal;
}
