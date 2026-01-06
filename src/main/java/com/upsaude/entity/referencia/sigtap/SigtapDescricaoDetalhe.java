package com.upsaude.entity.referencia.sigtap;

import com.upsaude.entity.BaseEntityWithoutTenant;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(
        name = "sigtap_descricao_detalhe",
        schema = "public",
        indexes = {
                @Index(name = "idx_sigtap_descricao_detalhe_detalhe_id", columnList = "detalhe_id"),
                @Index(name = "idx_sigtap_descricao_detalhe_competencia", columnList = "competencia_inicial")
        }
)
@Data
@EqualsAndHashCode(callSuper = true)
public class SigtapDescricaoDetalhe extends BaseEntityWithoutTenant {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "detalhe_id", nullable = false)
    private SigtapDetalhe detalhe;

    @Column(name = "descricao_completa", columnDefinition = "TEXT")
    private String descricaoCompleta;

    @Column(name = "competencia_inicial", length = 6)
    private String competenciaInicial;

    @Column(name = "competencia_final", length = 6)
    private String competenciaFinal;
}
