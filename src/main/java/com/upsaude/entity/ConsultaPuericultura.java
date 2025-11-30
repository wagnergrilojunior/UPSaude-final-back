package com.upsaude.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

/**
 * Entidade que representa uma consulta de puericultura.
 * Registra os dados de cada consulta de acompanhamento do crescimento e desenvolvimento da criança.
 *
 * @author UPSaúde
 */
@Entity
@Table(name = "consultas_puericultura", schema = "public",
       indexes = {
           @Index(name = "idx_consulta_puericultura_puericultura", columnList = "puericultura_id"),
           @Index(name = "idx_consulta_puericultura_data", columnList = "data_consulta"),
           @Index(name = "idx_consulta_puericultura_estabelecimento", columnList = "estabelecimento_id")
       })
@Data
@EqualsAndHashCode(callSuper = true)
public class ConsultaPuericultura extends BaseEntity {

    // ========== RELACIONAMENTOS ==========

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "puericultura_id", nullable = false)
    @NotNull(message = "Puericultura é obrigatória")
    private Puericultura puericultura;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profissional_id", nullable = false)
    @NotNull(message = "Profissional de saúde é obrigatório")
    private ProfissionaisSaude profissional;

    // ========== DADOS DA CONSULTA ==========

    @Column(name = "data_consulta", nullable = false)
    @NotNull(message = "Data da consulta é obrigatória")
    private OffsetDateTime dataConsulta;

    @Column(name = "numero_consulta")
    private Integer numeroConsulta;

    /** Idade em meses no momento da consulta */
    @Column(name = "idade_meses")
    private Integer idadeMeses;

    /** Idade em dias (complemento) */
    @Column(name = "idade_dias")
    private Integer idadeDias;

    // ========== ANTROPOMETRIA ==========

    @Column(name = "peso", precision = 6, scale = 2)
    private BigDecimal peso; // em gramas para bebês, kg para maiores

    @Column(name = "comprimento_estatura", precision = 5, scale = 1)
    private BigDecimal comprimentoEstatura; // em cm

    @Column(name = "perimetro_cefalico", precision = 4, scale = 1)
    private BigDecimal perimetroCefalico; // em cm

    @Column(name = "perimetro_toracico", precision = 4, scale = 1)
    private BigDecimal perimetroToracico; // em cm

    @Column(name = "imc", precision = 4, scale = 2)
    private BigDecimal imc;

    // ========== CLASSIFICAÇÃO NUTRICIONAL ==========

    @Size(max = 50, message = "Peso/Idade deve ter no máximo 50 caracteres")
    @Column(name = "peso_idade", length = 50)
    private String pesoIdade; // Adequado, Baixo, Elevado

    @Size(max = 50, message = "Estatura/Idade deve ter no máximo 50 caracteres")
    @Column(name = "estatura_idade", length = 50)
    private String estaturaIdade;

    @Size(max = 50, message = "Peso/Estatura deve ter no máximo 50 caracteres")
    @Column(name = "peso_estatura", length = 50)
    private String pesoEstatura;

    @Size(max = 50, message = "IMC/Idade deve ter no máximo 50 caracteres")
    @Column(name = "imc_idade", length = 50)
    private String imcIdade;

    @Size(max = 50, message = "Perímetro cefálico/Idade deve ter no máximo 50 caracteres")
    @Column(name = "perimetro_cefalico_idade", length = 50)
    private String perimetroCefalicoIdade;

    // ========== DESENVOLVIMENTO NEUROPSICOMOTOR ==========

    @Column(name = "desenvolvimento_adequado")
    private Boolean desenvolvimentoAdequado;

    @Column(name = "marcos_desenvolvimento", columnDefinition = "TEXT")
    private String marcosDesenvolvimento;

    @Column(name = "alteracoes_desenvolvimento", columnDefinition = "TEXT")
    private String alteracoesDesenvolvimento;

    // ========== ALEITAMENTO/ALIMENTAÇÃO ==========

    @Size(max = 100, message = "Tipo de aleitamento deve ter no máximo 100 caracteres")
    @Column(name = "tipo_aleitamento", length = 100)
    private String tipoAleitamento;

    @Column(name = "alimentacao_complementar", columnDefinition = "TEXT")
    private String alimentacaoComplementar;

    @Column(name = "dificuldades_alimentacao", columnDefinition = "TEXT")
    private String dificuldadesAlimentacao;

    // ========== VACINAÇÃO ==========

    @Column(name = "vacinacao_em_dia")
    private Boolean vacinacaoEmDia;

    @Column(name = "vacinas_atrasadas", columnDefinition = "TEXT")
    private String vacinasAtrasadas;

    @Column(name = "vacinas_aplicadas_consulta", columnDefinition = "TEXT")
    private String vacinasAplicadasConsulta;

    // ========== EXAME FÍSICO ==========

    @Column(name = "queixa_principal", columnDefinition = "TEXT")
    private String queixaPrincipal;

    @Column(name = "exame_fisico", columnDefinition = "TEXT")
    private String exameFisico;

    @Column(name = "reflexos", columnDefinition = "TEXT")
    private String reflexos;

    // ========== SUPLEMENTAÇÃO ==========

    @Column(name = "suplementacao_vitamina_a")
    private Boolean suplementacaoVitaminaA;

    @Column(name = "suplementacao_ferro")
    private Boolean suplementacaoFerro;

    @Column(name = "suplementacao_vitamina_d")
    private Boolean suplementacaoVitaminaD;

    // ========== CONDUTAS ==========

    @Column(name = "orientacoes", columnDefinition = "TEXT")
    private String orientacoes;

    @Column(name = "medicamentos_prescritos", columnDefinition = "TEXT")
    private String medicamentosPrescritos;

    @Column(name = "encaminhamentos", columnDefinition = "TEXT")
    private String encaminhamentos;

    // ========== PRÓXIMA CONSULTA ==========

    @Column(name = "data_proxima_consulta")
    private OffsetDateTime dataProximaConsulta;

    // ========== OBSERVAÇÕES ==========

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;
}

