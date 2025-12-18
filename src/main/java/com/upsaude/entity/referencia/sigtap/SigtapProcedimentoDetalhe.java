package com.upsaude.entity.referencia.sigtap;

import com.upsaude.entity.BaseEntityWithoutTenant;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Detalhes adicionais do procedimento (retorno do detalharProcedimento).
 *
 * <p>Os detalhes podem conter listas grandes; persistimos como JSONB para manter o escopo dentro
 * das entidades solicitadas (7 tabelas) e ainda assim preservar o conte?do retornado.
 */
@Entity
@Table(
        name = "sigtap_procedimento_detalhe",
        schema = "public",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_sigtap_procedimento_detalhe_procedimento", columnNames = {"procedimento_id"})
        },
        indexes = {
                @Index(name = "idx_sigtap_procedimento_detalhe_procedimento_id", columnList = "procedimento_id")
        }
)
@Data
@EqualsAndHashCode(callSuper = true)
public class SigtapProcedimentoDetalhe extends BaseEntityWithoutTenant {

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "procedimento_id", nullable = false)
    private SigtapProcedimento procedimento;

    @Column(name = "competencia_inicial", length = 6)
    private String competenciaInicial;

    @Column(name = "competencia_final", length = 6)
    private String competenciaFinal;

    @Column(name = "descricao_completa", columnDefinition = "TEXT")
    private String descricaoCompleta;

    @Column(name = "cids", columnDefinition = "jsonb")
    private String cids;

    @Column(name = "cbos", columnDefinition = "jsonb")
    private String cbos;

    @Column(name = "categorias_cbo", columnDefinition = "jsonb")
    private String categoriasCbo;

    @Column(name = "tipos_leito", columnDefinition = "jsonb")
    private String tiposLeito;

    @Column(name = "servicos_classificacoes", columnDefinition = "jsonb")
    private String servicosClassificacoes;

    @Column(name = "habilitacoes", columnDefinition = "jsonb")
    private String habilitacoes;

    @Column(name = "grupos_habilitacao", columnDefinition = "jsonb")
    private String gruposHabilitacao;

    @Column(name = "incrementos", columnDefinition = "jsonb")
    private String incrementos;

    @Column(name = "componentes_rede", columnDefinition = "jsonb")
    private String componentesRede;

    @Column(name = "origens_sigtap", columnDefinition = "jsonb")
    private String origensSigtap;

    @Column(name = "origens_sia_sih", columnDefinition = "jsonb")
    private String origensSiaSih;

    @Column(name = "regras_condicionadas", columnDefinition = "jsonb")
    private String regrasCondicionadas;

    @Column(name = "renases", columnDefinition = "jsonb")
    private String renases;

    @Column(name = "tuss", columnDefinition = "jsonb")
    private String tuss;
}

