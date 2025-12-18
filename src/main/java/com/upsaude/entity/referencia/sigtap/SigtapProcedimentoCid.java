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
 * Relacionamento entre Procedimento e CID.
 */
@Entity
@Table(
        name = "sigtap_procedimento_cid",
        schema = "public",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_sigtap_proc_cid_proc_cid_comp", columnNames = {"procedimento_id", "cid_id", "competencia_inicial"})
        },
        indexes = {
                @Index(name = "idx_sigtap_proc_cid_procedimento_id", columnList = "procedimento_id"),
                @Index(name = "idx_sigtap_proc_cid_cid_id", columnList = "cid_id")
        }
)
@Data
@EqualsAndHashCode(callSuper = true)
public class SigtapProcedimentoCid extends BaseEntityWithoutTenant {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "procedimento_id", nullable = false)
    private SigtapProcedimento procedimento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cid_id", nullable = false)
    private SigtapCid cid;

    @Column(name = "principal", nullable = false)
    private Boolean principal = false;

    @Column(name = "competencia_inicial", length = 6)
    private String competenciaInicial;

    @Column(name = "competencia_final", length = 6)
    private String competenciaFinal;
}
