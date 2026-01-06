package com.upsaude.entity.referencia.sigtap;

import com.upsaude.entity.BaseEntityWithoutTenant;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(
        name = "sigtap_procedimento_regra_condicionada",
        schema = "public",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_sigtap_proc_regra_proc_regra", columnNames = {"procedimento_id", "regra_condicionada_id"})
        },
        indexes = {
                @Index(name = "idx_sigtap_proc_regra_procedimento_id", columnList = "procedimento_id"),
                @Index(name = "idx_sigtap_proc_regra_regra_condicionada_id", columnList = "regra_condicionada_id")
        }
)
@Data
@EqualsAndHashCode(callSuper = true)
public class SigtapProcedimentoRegraCondicionada extends BaseEntityWithoutTenant {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "procedimento_id", nullable = false)
    private SigtapProcedimento procedimento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "regra_condicionada_id", nullable = false)
    private SigtapRegraCondicionada regraCondicionada;
}
