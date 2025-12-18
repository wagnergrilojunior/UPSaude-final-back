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

/**
 * Relacionamento entre Procedimento e SIA/SIH.
 */
@Entity
@Table(
        name = "sigtap_procedimento_sia_sih",
        schema = "public",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_sigtap_proc_sia_sih_proc_sia_comp", columnNames = {"procedimento_id", "sia_sih_id", "competencia_inicial"})
        },
        indexes = {
                @Index(name = "idx_sigtap_proc_sia_sih_procedimento_id", columnList = "procedimento_id"),
                @Index(name = "idx_sigtap_proc_sia_sih_sia_sih_id", columnList = "sia_sih_id")
        }
)
@Data
@EqualsAndHashCode(callSuper = true)
public class SigtapProcedimentoSiaSih extends BaseEntityWithoutTenant {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "procedimento_id", nullable = false)
    private SigtapProcedimento procedimento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sia_sih_id", nullable = false)
    private SigtapSiaSih siaSih;

    @Column(name = "tipo_procedimento", length = 1)
    private String tipoProcedimento;

    @Column(name = "competencia_inicial", length = 6)
    private String competenciaInicial;

    @Column(name = "competencia_final", length = 6)
    private String competenciaFinal;
}
