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

@Entity
@Table(
        name = "sigtap_excecao_compatibilidade",
        schema = "public",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_sigtap_exc_comp_chave_natural", columnNames = {"procedimento_restricao_id", "procedimento_principal_id", "procedimento_compativel_id", "competencia_inicial"})
        },
        indexes = {
                @Index(name = "idx_sigtap_exc_comp_restricao_id", columnList = "procedimento_restricao_id"),
                @Index(name = "idx_sigtap_exc_comp_principal_id", columnList = "procedimento_principal_id"),
                @Index(name = "idx_sigtap_exc_comp_compativel_id", columnList = "procedimento_compativel_id")
        }
)
@Data
@EqualsAndHashCode(callSuper = true)
public class SigtapExcecaoCompatibilidade extends BaseEntityWithoutTenant {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "procedimento_restricao_id", nullable = false)
    private SigtapProcedimento procedimentoRestricao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "procedimento_principal_id", nullable = false)
    private SigtapProcedimento procedimentoPrincipal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "registro_principal_id")
    private SigtapRegistro registroPrincipal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "procedimento_compativel_id", nullable = false)
    private SigtapProcedimento procedimentoCompativel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "registro_compativel_id")
    private SigtapRegistro registroCompativel;

    @Column(name = "tipo_compatibilidade", length = 1)
    private String tipoCompatibilidade;

    @Column(name = "competencia_inicial", length = 6)
    private String competenciaInicial;

    @Column(name = "competencia_final", length = 6)
    private String competenciaFinal;
}
