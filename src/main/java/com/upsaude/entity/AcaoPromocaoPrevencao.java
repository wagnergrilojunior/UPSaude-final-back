package com.upsaude.entity;

import com.upsaude.enums.TipoAcaoPromocaoSaudeEnum;
import com.upsaude.util.converter.TipoAcaoPromocaoSaudeEnumConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidade que representa uma ação de promoção e prevenção em saúde.
 * Armazena informações sobre programas e ações de saúde pública.
 *
 * @author UPSaúde
 */
@Entity
@Table(name = "acoes_promocao_prevencao", schema = "public",
       indexes = {
           @Index(name = "idx_acao_tipo", columnList = "tipo_acao"),
           @Index(name = "idx_acao_data_inicio", columnList = "data_inicio"),
           @Index(name = "idx_acao_estabelecimento", columnList = "estabelecimento_id")
       })
@Data
@EqualsAndHashCode(callSuper = true)
public class AcaoPromocaoPrevencao extends BaseEntity {

    // ========== RELACIONAMENTOS ==========

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profissional_responsavel_id", nullable = false)
    @NotNull(message = "Profissional responsável é obrigatório")
    private ProfissionaisSaude profissionalResponsavel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipe_saude_id")
    private EquipeSaude equipeSaude;

    /** Profissionais que participaram da ação */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "acoes_promocao_profissionais",
        schema = "public",
        joinColumns = @JoinColumn(name = "acao_id"),
        inverseJoinColumns = @JoinColumn(name = "profissional_id")
    )
    private List<ProfissionaisSaude> profissionaisParticipantes = new ArrayList<>();

    // ========== TIPO DE AÇÃO ==========

    @Convert(converter = TipoAcaoPromocaoSaudeEnumConverter.class)
    @Column(name = "tipo_acao", nullable = false)
    @NotNull(message = "Tipo de ação é obrigatório")
    private TipoAcaoPromocaoSaudeEnum tipoAcao;

    // ========== INFORMAÇÕES DA AÇÃO ==========

    @Size(max = 255, message = "Nome deve ter no máximo 255 caracteres")
    @Column(name = "nome", nullable = false, length = 255)
    @NotNull(message = "Nome é obrigatório")
    private String nome;

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "objetivos", columnDefinition = "TEXT")
    private String objetivos;

    @Column(name = "justificativa", columnDefinition = "TEXT")
    private String justificativa;

    @Column(name = "metodologia", columnDefinition = "TEXT")
    private String metodologia;

    // ========== PERÍODO ==========

    @Column(name = "data_inicio", nullable = false)
    @NotNull(message = "Data de início é obrigatória")
    private LocalDate dataInicio;

    @Column(name = "data_fim")
    private LocalDate dataFim;

    @Column(name = "acao_continua", nullable = false)
    private Boolean acaoContinua = false;

    @Size(max = 100, message = "Periodicidade deve ter no máximo 100 caracteres")
    @Column(name = "periodicidade", length = 100)
    private String periodicidade; // Semanal, Mensal, Anual, etc.

    // ========== LOCAL ==========

    @Size(max = 255, message = "Local deve ter no máximo 255 caracteres")
    @Column(name = "local", length = 255)
    private String local;

    @Column(name = "abrangencia_territorial", columnDefinition = "TEXT")
    private String abrangenciaTerritorial;

    // ========== PÚBLICO-ALVO ==========

    @Size(max = 255, message = "Público-alvo deve ter no máximo 255 caracteres")
    @Column(name = "publico_alvo", length = 255)
    private String publicoAlvo;

    @Column(name = "populacao_estimada")
    @Min(value = 0, message = "População estimada não pode ser negativa")
    private Integer populacaoEstimada;

    @Column(name = "criterios_inclusao", columnDefinition = "TEXT")
    private String criteriosInclusao;

    // ========== METAS E INDICADORES ==========

    @Column(name = "meta_cobertura")
    private Integer metaCobertura; // Percentual de cobertura esperado

    @Column(name = "meta_atendimentos")
    @Min(value = 0, message = "Meta de atendimentos não pode ser negativa")
    private Integer metaAtendimentos;

    @Column(name = "indicadores_acompanhamento", columnDefinition = "TEXT")
    private String indicadoresAcompanhamento;

    // ========== RESULTADOS ==========

    @Column(name = "numero_atendimentos_realizados")
    @Min(value = 0, message = "Número de atendimentos não pode ser negativo")
    private Integer numeroAtendimentosRealizados;

    @Column(name = "cobertura_alcancada")
    private Integer coberturaAlcancada; // Percentual

    @Column(name = "resultados_alcancados", columnDefinition = "TEXT")
    private String resultadosAlcancados;

    @Column(name = "dificuldades_encontradas", columnDefinition = "TEXT")
    private String dificuldadesEncontradas;

    @Column(name = "licoes_aprendidas", columnDefinition = "TEXT")
    private String licoesAprendidas;

    // ========== RECURSOS ==========

    @Column(name = "recursos_necessarios", columnDefinition = "TEXT")
    private String recursosNecessarios;

    @Column(name = "recursos_utilizados", columnDefinition = "TEXT")
    private String recursosUtilizados;

    @Column(name = "parcerias", columnDefinition = "TEXT")
    private String parcerias;

    // ========== STATUS ==========

    @Column(name = "status_acao", nullable = false)
    @Size(max = 50, message = "Status deve ter no máximo 50 caracteres")
    private String statusAcao = "PLANEJADA"; // PLANEJADA, EM_ANDAMENTO, CONCLUIDA, CANCELADA

    @Column(name = "data_inicio_execucao")
    private LocalDate dataInicioExecucao;

    @Column(name = "data_conclusao")
    private LocalDate dataConclusao;

    // ========== OBSERVAÇÕES ==========

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;
}

