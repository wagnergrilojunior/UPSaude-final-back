package com.upsaude.entity.saude_publica.planejamento;
import com.upsaude.entity.BaseEntity;

import com.upsaude.entity.profissional.ProfissionaisSaude;
import com.upsaude.entity.profissional.equipe.EquipeSaude;

import com.upsaude.entity.paciente.Paciente;

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

    @Column(name = "data_ultima_menstruacao")
    private LocalDate dataUltimaMenstruacao;

    @Column(name = "data_provavel_parto")
    private LocalDate dataProvavelParto;

    @Column(name = "idade_gestacional_cadastro")
    @Min(value = 0, message = "Idade gestacional não pode ser negativa")
    @Max(value = 45, message = "Idade gestacional não pode ser maior que 45 semanas")
    private Integer idadeGestacionalCadastro;

    @Column(name = "gestacoes_anteriores")
    @Min(value = 0, message = "Número de gestações não pode ser negativo")
    private Integer gestacoesAnteriores;

    @Column(name = "partos")
    @Min(value = 0, message = "Número de partos não pode ser negativo")
    private Integer partos;

    @Column(name = "abortos")
    @Min(value = 0, message = "Número de abortos não pode ser negativo")
    private Integer abortos;

    @Column(name = "filhos_vivos")
    @Min(value = 0, message = "Número de filhos vivos não pode ser negativo")
    private Integer filhosVivos;

    @Column(name = "partos_vaginais")
    @Min(value = 0, message = "Número de partos vaginais não pode ser negativo")
    private Integer partosVaginais;

    @Column(name = "cesareas")
    @Min(value = 0, message = "Número de cesáreas não pode ser negativo")
    private Integer cesareas;

    @Column(name = "partos_prematuros")
    @Min(value = 0, message = "Número de partos prematuros não pode ser negativo")
    private Integer partosPrematuros;

    @Column(name = "natimortos")
    @Min(value = 0, message = "Número de natimortos não pode ser negativo")
    private Integer natimortos;

    @Convert(converter = ClassificacaoRiscoGestacionalEnumConverter.class)
    @Column(name = "classificacao_risco")
    private ClassificacaoRiscoGestacionalEnum classificacaoRisco;

    @Column(name = "motivos_alto_risco", columnDefinition = "TEXT")
    private String motivosAltoRisco;

    @Convert(converter = StatusPreNatalEnumConverter.class)
    @Column(name = "status_pre_natal", nullable = false)
    @NotNull(message = "Status do pré-natal é obrigatório")
    private StatusPreNatalEnum statusPreNatal = StatusPreNatalEnum.EM_ACOMPANHAMENTO;

    @Column(name = "data_inicio_acompanhamento")
    private LocalDate dataInicioAcompanhamento;

    @Column(name = "data_encerramento")
    private LocalDate dataEncerramento;

    @Column(name = "data_parto")
    private OffsetDateTime dataParto;

    @Column(name = "idade_gestacional_parto")
    @Min(value = 0, message = "Idade gestacional no parto não pode ser negativa")
    @Max(value = 45, message = "Idade gestacional no parto não pode ser maior que 45 semanas")
    private Integer idadeGestacionalParto;

    @Size(max = 50, message = "Tipo de parto deve ter no máximo 50 caracteres")
    @Column(name = "tipo_parto", length = 50)
    private String tipoParto;

    @Size(max = 255, message = "Local do parto deve ter no máximo 255 caracteres")
    @Column(name = "local_parto", length = 255)
    private String localParto;

    @Column(name = "peso_nascimento")
    private BigDecimal pesoNascimento;

    @Column(name = "comprimento_nascimento")
    private BigDecimal comprimentoNascimento;

    @Column(name = "apgar_1_minuto")
    @Min(value = 0, message = "Apgar não pode ser negativo")
    @Max(value = 10, message = "Apgar não pode ser maior que 10")
    private Integer apgar1Minuto;

    @Column(name = "apgar_5_minutos")
    @Min(value = 0, message = "Apgar não pode ser negativo")
    @Max(value = 10, message = "Apgar não pode ser maior que 10")
    private Integer apgar5Minutos;

    @Column(name = "perimetro_cefalico")
    private BigDecimal perimetroCefalico;

    @Size(max = 10, message = "Tipo sanguíneo deve ter no máximo 10 caracteres")
    @Column(name = "tipo_sanguineo", length = 10)
    private String tipoSanguineo;

    @Size(max = 10, message = "Fator Rh deve ter no máximo 10 caracteres")
    @Column(name = "fator_rh", length = 10)
    private String fatorRh;

    @Column(name = "peso_pre_gestacional", precision = 5, scale = 2)
    private BigDecimal pesoPreGestacional;

    @Column(name = "altura", precision = 3, scale = 2)
    private BigDecimal altura;

    @Column(name = "imc_pre_gestacional", precision = 4, scale = 2)
    private BigDecimal imcPreGestacional;

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

    @Column(name = "vacina_dtpa_realizada", nullable = false)
    private Boolean vacinaDtpaRealizada = false;

    @Column(name = "vacina_hepatite_b_realizada", nullable = false)
    private Boolean vacinaHepatiteBRealizada = false;

    @Column(name = "vacina_gripe_realizada", nullable = false)
    private Boolean vacinaGripeRealizada = false;

    @Column(name = "numero_consultas_pre_natal")
    @Min(value = 0, message = "Número de consultas não pode ser negativo")
    private Integer numeroConsultasPreNatal = 0;

    @Column(name = "antecedentes_familiares", columnDefinition = "TEXT")
    private String antecedentesFamiliares;

    @Column(name = "antecedentes_pessoais", columnDefinition = "TEXT")
    private String antecedentesPessoais;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    @Column(name = "observacoes_internas", columnDefinition = "TEXT")
    private String observacoesInternas;
}
