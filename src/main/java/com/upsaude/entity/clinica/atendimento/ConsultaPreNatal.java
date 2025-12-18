package com.upsaude.entity.clinica.atendimento;
import com.upsaude.entity.BaseEntity;

import com.upsaude.entity.profissional.ProfissionaisSaude;
import com.upsaude.entity.saude_publica.planejamento.PreNatal;

import jakarta.persistence.Column;
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
import java.time.OffsetDateTime;

@Entity
@Table(name = "consultas_pre_natal", schema = "public",
       indexes = {
           @Index(name = "idx_consulta_prenatal_prenatal", columnList = "pre_natal_id"),
           @Index(name = "idx_consulta_prenatal_data", columnList = "data_consulta"),
           @Index(name = "idx_consulta_prenatal_estabelecimento", columnList = "estabelecimento_id")
       })
@Data
@EqualsAndHashCode(callSuper = true)
public class ConsultaPreNatal extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pre_natal_id", nullable = false)
    @NotNull(message = "Pré-natal é obrigatório")
    private PreNatal preNatal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profissional_id", nullable = false)
    @NotNull(message = "Profissional de saúde é obrigatório")
    private ProfissionaisSaude profissional;

    @Column(name = "data_consulta", nullable = false)
    @NotNull(message = "Data da consulta é obrigatória")
    private OffsetDateTime dataConsulta;

    @Column(name = "numero_consulta")
    @Min(value = 1, message = "Número da consulta deve ser no mínimo 1")
    private Integer numeroConsulta;

    @Column(name = "idade_gestacional_semanas")
    @Min(value = 0, message = "Idade gestacional não pode ser negativa")
    @Max(value = 45, message = "Idade gestacional não pode ser maior que 45 semanas")
    private Integer idadeGestacionalSemanas;

    @Column(name = "idade_gestacional_dias")
    @Min(value = 0, message = "Dias não pode ser negativo")
    @Max(value = 6, message = "Dias não pode ser maior que 6")
    private Integer idadeGestacionalDias;

    @Column(name = "peso", precision = 5, scale = 2)
    private BigDecimal peso;

    @Column(name = "pressao_arterial_sistolica")
    @Min(value = 0, message = "Pressão sistólica não pode ser negativa")
    private Integer pressaoArterialSistolica;

    @Column(name = "pressao_arterial_diastolica")
    @Min(value = 0, message = "Pressão diastólica não pode ser negativa")
    private Integer pressaoArterialDiastolica;

    @Column(name = "edema")
    private Boolean edema;

    @Size(max = 50, message = "Localização do edema deve ter no máximo 50 caracteres")
    @Column(name = "edema_localizacao", length = 50)
    private String edemaLocalizacao;

    @Column(name = "altura_uterina", precision = 4, scale = 1)
    private BigDecimal alturaUterina;

    @Column(name = "bcf")
    @Min(value = 0, message = "BCF não pode ser negativo")
    private Integer bcf;

    @Column(name = "movimentos_fetais")
    private Boolean movimentosFetais;

    @Size(max = 50, message = "Apresentação fetal deve ter no máximo 50 caracteres")
    @Column(name = "apresentacao_fetal", length = 50)
    private String apresentacaoFetal;

    @Size(max = 50, message = "Posição fetal deve ter no máximo 50 caracteres")
    @Column(name = "posicao_fetal", length = 50)
    private String posicaoFetal;

    @Column(name = "queixa_principal", columnDefinition = "TEXT")
    private String queixaPrincipal;

    @Column(name = "nauseas_vomitos")
    private Boolean nauseasVomitos;

    @Column(name = "sangramento")
    private Boolean sangramento;

    @Column(name = "contracao_uterina")
    private Boolean contracaoUterina;

    @Column(name = "perda_liquido")
    private Boolean perdaLiquido;

    @Column(name = "cefaleia")
    private Boolean cefaleia;

    @Column(name = "epigastralgia")
    private Boolean epigastralgia;

    @Column(name = "disturbios_visuais")
    private Boolean disturbiosVisuais;

    @Column(name = "exames_solicitados", columnDefinition = "TEXT")
    private String examesSolicitados;

    @Column(name = "resultados_exames", columnDefinition = "TEXT")
    private String resultadosExames;

    @Column(name = "suplementacao_acido_folico")
    private Boolean suplementacaoAcidoFolico;

    @Column(name = "suplementacao_sulfato_ferroso")
    private Boolean suplementacaoSulfatoFerroso;

    @Column(name = "medicamentos_prescritos", columnDefinition = "TEXT")
    private String medicamentosPrescritos;

    @Column(name = "orientacoes", columnDefinition = "TEXT")
    private String orientacoes;

    @Column(name = "encaminhamentos", columnDefinition = "TEXT")
    private String encaminhamentos;

    @Column(name = "data_proxima_consulta")
    private OffsetDateTime dataProximaConsulta;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;
}
