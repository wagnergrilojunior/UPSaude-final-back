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

/**
 * Procedimento SIGTAP.
 *
 * <p>Representa o cadastro principal do procedimento. Detalhes adicionais (listas) ficam em
 * {@link SigtapProcedimentoDetalhe}.
 */
@Entity
@Table(
        name = "sigtap_procedimento",
        schema = "public",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_sigtap_procedimento_codigo_comp_ini", columnNames = {"codigo_oficial", "competencia_inicial"})
        },
        indexes = {
                @Index(name = "idx_sigtap_procedimento_nome", columnList = "nome"),
                @Index(name = "idx_sigtap_procedimento_forma_org", columnList = "forma_organizacao_id")
        }
)
@Data
@EqualsAndHashCode(callSuper = true)
public class SigtapProcedimento extends BaseEntityWithoutTenant {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "forma_organizacao_id")
    private SigtapFormaOrganizacao formaOrganizacao;

    @Column(name = "codigo_oficial", nullable = false, length = 20)
    private String codigoOficial;

    @Column(name = "nome", length = 255)
    private String nome;

    @Column(name = "competencia_inicial", length = 6)
    private String competenciaInicial;

    @Column(name = "competencia_final", length = 6)
    private String competenciaFinal;

    @Column(name = "tipo_complexidade", length = 1)
    private String tipoComplexidade;

    @Column(name = "sexo_permitido", length = 30)
    private String sexoPermitido;

    @Column(name = "idade_minima")
    private Integer idadeMinima;

    @Column(name = "idade_maxima")
    private Integer idadeMaxima;

    @Column(name = "media_dias_internacao")
    private Integer mediaDiasInternacao;

    @Column(name = "quantidade_maxima_dias")
    private Integer quantidadeMaximaDias;

    @Column(name = "limite_maximo")
    private Integer limiteMaximo;

    @Column(name = "pontos")
    private Integer pontos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "financiamento_id")
    private SigtapFinanciamento financiamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rubrica_id")
    private SigtapRubrica rubrica;

    @Column(name = "valor_servico_hospitalar", precision = 14, scale = 2)
    private BigDecimal valorServicoHospitalar;

    @Column(name = "valor_servico_ambulatorial", precision = 14, scale = 2)
    private BigDecimal valorServicoAmbulatorial;

    @Column(name = "valor_servico_profissional", precision = 14, scale = 2)
    private BigDecimal valorServicoProfissional;
}

