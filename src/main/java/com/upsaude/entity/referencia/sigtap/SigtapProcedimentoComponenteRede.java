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
        name = "sigtap_procedimento_componente_rede",
        schema = "public",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_sigtap_proc_comp_rede_proc_comp", columnNames = {"procedimento_id", "componente_rede_id"})
        },
        indexes = {
                @Index(name = "idx_sigtap_proc_comp_rede_procedimento_id", columnList = "procedimento_id"),
                @Index(name = "idx_sigtap_proc_comp_rede_componente_rede_id", columnList = "componente_rede_id")
        }
)
@Data
@EqualsAndHashCode(callSuper = true)
public class SigtapProcedimentoComponenteRede extends BaseEntityWithoutTenant {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "procedimento_id", nullable = false)
    private SigtapProcedimento procedimento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "componente_rede_id", nullable = false)
    private SigtapComponenteRede componenteRede;
}
