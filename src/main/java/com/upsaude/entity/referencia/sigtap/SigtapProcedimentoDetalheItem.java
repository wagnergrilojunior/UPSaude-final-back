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
        name = "sigtap_procedimento_detalhe_item",
        schema = "public",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_sigtap_proc_det_item_proc_det", columnNames = {"procedimento_id", "detalhe_id"})
        },
        indexes = {
                @Index(name = "idx_sigtap_proc_det_item_procedimento_id", columnList = "procedimento_id"),
                @Index(name = "idx_sigtap_proc_det_item_detalhe_id", columnList = "detalhe_id")
        }
)
@Data
@EqualsAndHashCode(callSuper = true)
public class SigtapProcedimentoDetalheItem extends BaseEntityWithoutTenant {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "procedimento_id", nullable = false)
    private SigtapProcedimento procedimento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "detalhe_id", nullable = false)
    private SigtapDetalhe detalhe;
}
