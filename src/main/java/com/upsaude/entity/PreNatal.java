package com.upsaude.entity;

import com.upsaude.enums.ClassificacaoRiscoGestacionalEnum;
import com.upsaude.enums.StatusPreNatalEnum;
import com.upsaude.util.converter.ClassificacaoRiscoGestacionalEnumConverter;
import com.upsaude.util.converter.StatusPreNatalEnumConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

/**
 * Entidade que representa o acompanhamento pré-natal de uma gestante.
 * Armazena informações completas sobre o pré-natal conforme diretrizes do Ministério da Saúde.
 *
 * @author UPSaúde
 */
@Entity
@Table(name = "pre_natal", schema = "public",
       indexes = {
           @Index(name = "idx_prenatal_paciente", columnList = "paciente_id"),
           @Index(name = "idx_prenatal_status", columnList = "status_pre_natal"),
           @Index(name = "idx_prenatal_dum", columnList = "data_ultima_menstruacao"),
           @Index(name = "idx_prenatal_dpp", columnList = "data_provavel_parto"),
           @Index(name = "idx_prenatal_estabelecimento", columnList = "estabelecimento_id")
       })
@Data
@EqualsAndHashCode(callSuper = true)
public class PreNatal extends BaseEntity {

    // ========== RELACIONAMENTOS ==========

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    @NotNull(message = "Paciente (gestante) é obrigatório")
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profissional_responsavel_id")
    private ProfissionaisSaude profissionalResponsavel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipe_saude_id")
    private EquipeSaude equipeSaude;

    // ========== DADOS OBSTÉTRICOS ==========

    /** Data da última menstruação (DUM) */
    @Column(name = "data_ultima_menstruacao")
    private LocalDate dataUltimaMenstruacao;

    /** Data provável do parto (DPP) - calculada ou corrigida por USG */
    @Column(name = "data_provavel_parto")
    private LocalDate dataProvavelParto;

    /** Idade gestacional em semanas no momento do cadastro */
    @Column(name = "idade_gestacional_cadastro")
    @Min(value = 0, message = "Idade gestacional não pode ser negativa")
    @Max(value = 45, message = "Idade gestacional não pode ser maior que 45 semanas")
    private Integer idadeGestacionalCadastro;

    /** Número de gestações anteriores (Gesta) */
    @Column(name = "gestacoes_anteriores")
    @Min(value = 0, message = "Número de gestações não pode ser negativo")
    private Integer gestacoesAnteriores;

    /** Número de partos */
    @Column(name = "partos")
    @Min(value = 0, message = "Número de partos não pode ser negativo")
    private Integer partos;

    /** Número de abortos */
    @Column(name = "abortos")
    @Min(value = 0, message = "Número de abortos não pode ser negativo")
    private Integer abortos;

    /** Número de filhos vivos */
    @Column(name = "filhos_vivos")
    @Min(value = 0, message = "Número de filhos vivos não pode ser negativo")
    private Integer filhosVivos;

    /** Número de partos vaginais anteriores */
    @Column(name = "partos_vaginais")
    @Min(value = 0, message = "Número de partos vaginais não pode ser negativo")
    private Integer partosVaginais;

    /** Número de cesáreas anteriores */
    @Column(name = "cesareas")
    @Min(value = 0, message = "Número de cesáreas não pode ser negativo")
    private Integer cesareas;

    /** Número de partos prematuros anteriores */
    @Column(name = "partos_prematuros")
    @Min(value = 0, message = "Número de partos prematuros não pode ser negativo")
    private Integer partosPrematuros;

    /** Número de natimortos */
    @Column(name = "natimortos")
    @Min(value = 0, message = "Número de natimortos não pode ser negativo")
    private Integer natimortos;

    // ========== CLASSIFICAÇÃO DE RISCO ==========

    @Convert(converter = ClassificacaoRiscoGestacionalEnumConverter.class)
    @Column(name = "classificacao_risco")
    private ClassificacaoRiscoGestacionalEnum classificacaoRisco;

    @Column(name = "motivos_alto_risco", columnDefinition = "TEXT")
    private String motivosAltoRisco;

    // ========== STATUS DO PRÉ-NATAL ==========

    @Convert(converter = StatusPreNatalEnumConverter.class)
    @Column(name = "status_pre_natal", nullable = false)
    @NotNull(message = "Status do pré-natal é obrigatório")
    private StatusPreNatalEnum statusPreNatal = StatusPreNatalEnum.EM_ACOMPANHAMENTO;

    /** Data de início do acompanhamento pré-natal */
    @Column(name = "data_inicio_acompanhamento")
    private LocalDate dataInicioAcompanhamento;

    /** Data de encerramento do acompanhamento (parto, aborto, etc.) */
    @Column(name = "data_encerramento")
    private LocalDate dataEncerramento;

    // ========== DADOS DO PARTO (preenchido ao final) ==========

    @Column(name = "data_parto")
    private OffsetDateTime dataParto;

    @Column(name = "idade_gestacional_parto")
    @Min(value = 0, message = "Idade gestacional no parto não pode ser negativa")
    @Max(value = 45, message = "Idade gestacional no parto não pode ser maior que 45 semanas")
    private Integer idadeGestacionalParto;

    @Size(max = 50, message = "Tipo de parto deve ter no máximo 50 caracteres")
    @Column(name = "tipo_parto", length = 50)
    private String tipoParto; // Normal, Cesárea, Fórceps

    @Size(max = 255, message = "Local do parto deve ter no máximo 255 caracteres")
    @Column(name = "local_parto", length = 255)
    private String localParto;

    // ========== DADOS DO RECÉM-NASCIDO ==========

    @Column(name = "peso_nascimento")
    private BigDecimal pesoNascimento; // em gramas

    @Column(name = "comprimento_nascimento")
    private BigDecimal comprimentoNascimento; // em cm

    @Column(name = "apgar_1_minuto")
    @Min(value = 0, message = "Apgar não pode ser negativo")
    @Max(value = 10, message = "Apgar não pode ser maior que 10")
    private Integer apgar1Minuto;

    @Column(name = "apgar_5_minutos")
    @Min(value = 0, message = "Apgar não pode ser negativo")
    @Max(value = 10, message = "Apgar não pode ser maior que 10")
    private Integer apgar5Minutos;

    @Column(name = "perimetro_cefalico")
    private BigDecimal perimetroCefalico; // em cm

    // ========== INFORMAÇÕES DE SAÚDE ==========

    /** Tipo sanguíneo da gestante */
    @Size(max = 10, message = "Tipo sanguíneo deve ter no máximo 10 caracteres")
    @Column(name = "tipo_sanguineo", length = 10)
    private String tipoSanguineo;

    /** Fator Rh */
    @Size(max = 10, message = "Fator Rh deve ter no máximo 10 caracteres")
    @Column(name = "fator_rh", length = 10)
    private String fatorRh;

    /** Peso pré-gestacional */
    @Column(name = "peso_pre_gestacional", precision = 5, scale = 2)
    private BigDecimal pesoPreGestacional;

    /** Altura da gestante */
    @Column(name = "altura", precision = 3, scale = 2)
    private BigDecimal altura;

    /** IMC pré-gestacional */
    @Column(name = "imc_pre_gestacional", precision = 4, scale = 2)
    private BigDecimal imcPreGestacional;

    // ========== EXAMES REALIZADOS ==========

    @Column(name = "exame_sifilis_realizado", nullable = false)
    private Boolean exameSifilisRealizado = false;

    @Column(name = "exame_hiv_realizado", nullable = false)
    private Boolean exameHivRealizado = false;

    @Column(name = "exame_hepatite_b_realizado", nullable = false)
    private Boolean exameHepatiteBRealizado = false;

    @Column(name = "exame_toxoplasmose_realizado", nullable = false)
    private Boolean exameToxoplasmoseRealizado = false;

    @Column(name = "ultrassonografia_realizada", nullable = false)
    private Boolean ultrassonografiaRealizada = false;

    @Column(name = "data_primeira_ultrassonografia")
    private LocalDate dataPrimeiraUltrassonografia;

    // ========== VACINAS ==========

    @Column(name = "vacina_dtpa_realizada", nullable = false)
    private Boolean vacinaDtpaRealizada = false;

    @Column(name = "vacina_hepatite_b_realizada", nullable = false)
    private Boolean vacinaHepatiteBRealizada = false;

    @Column(name = "vacina_gripe_realizada", nullable = false)
    private Boolean vacinaGripeRealizada = false;

    // ========== NÚMERO DE CONSULTAS ==========

    @Column(name = "numero_consultas_pre_natal")
    @Min(value = 0, message = "Número de consultas não pode ser negativo")
    private Integer numeroConsultasPreNatal = 0;

    // ========== OBSERVAÇÕES ==========

    @Column(name = "antecedentes_familiares", columnDefinition = "TEXT")
    private String antecedentesFamiliares;

    @Column(name = "antecedentes_pessoais", columnDefinition = "TEXT")
    private String antecedentesPessoais;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    @Column(name = "observacoes_internas", columnDefinition = "TEXT")
    private String observacoesInternas;
}

