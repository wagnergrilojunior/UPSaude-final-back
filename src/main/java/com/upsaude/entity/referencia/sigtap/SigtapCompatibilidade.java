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
        name = "sigtap_compatibilidade",
        schema = "public",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_sigtap_compatibilidade_chave_natural",
                        columnNames = {"compatibilidade_possivel_id", "procedimento_principal_id", "procedimento_secundario_id", "competencia_inicial"}
                )
        },
        indexes = {
                @Index(name = "idx_sigtap_compatibilidade_principal", columnList = "procedimento_principal_id"),
                @Index(name = "idx_sigtap_compatibilidade_secundario", columnList = "procedimento_secundario_id")
        }
)
@Data
@EqualsAndHashCode(callSuper = true)
public class SigtapCompatibilidade extends BaseEntityWithoutTenant {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "compatibilidade_possivel_id", nullable = false)
    private SigtapCompatibilidadePossivel compatibilidadePossivel;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "procedimento_principal_id", nullable = false)
    private SigtapProcedimento procedimentoPrincipal;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "procedimento_secundario_id", nullable = false)
    private SigtapProcedimento procedimentoSecundario;

    @Column(name = "competencia_inicial", length = 6)
    private String competenciaInicial;

    @Column(name = "competencia_final", length = 6)
    private String competenciaFinal;

    @Column(name = "quantidade_permitida")
    private Integer quantidadePermitida;

    @Column(name = "documento_publicacao", columnDefinition = "jsonb")
    private String documentoPublicacao;

    @Column(name = "documento_revogacao", columnDefinition = "jsonb")
    private String documentoRevogacao;
}
