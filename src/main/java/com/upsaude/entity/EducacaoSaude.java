package com.upsaude.entity;

import com.upsaude.enums.TipoEducacaoSaudeEnum;
import com.upsaude.util.converter.TipoEducacaoSaudeEnumConverter;
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

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidade que representa uma ação de educação em saúde.
 * Armazena informações sobre atividades educativas realizadas na unidade de saúde ou comunidade.
 *
 * @author UPSaúde
 */
@Entity
@Table(name = "educacao_saude", schema = "public",
       indexes = {
           @Index(name = "idx_educacao_tipo", columnList = "tipo_atividade"),
           @Index(name = "idx_educacao_data", columnList = "data_hora_inicio"),
           @Index(name = "idx_educacao_estabelecimento", columnList = "estabelecimento_id")
       })
@Data
@EqualsAndHashCode(callSuper = true)
public class EducacaoSaude extends BaseEntity {

    // ========== RELACIONAMENTOS ==========

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profissional_responsavel_id", nullable = false)
    @NotNull(message = "Profissional responsável é obrigatório")
    private ProfissionaisSaude profissionalResponsavel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipe_saude_id")
    private EquipeSaude equipeSaude;

    /** Participantes da atividade (quando são pacientes cadastrados) */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "educacao_saude_participantes",
        schema = "public",
        joinColumns = @JoinColumn(name = "educacao_saude_id"),
        inverseJoinColumns = @JoinColumn(name = "paciente_id")
    )
    private List<Paciente> participantes = new ArrayList<>();

    /** Profissionais que participaram da atividade */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "educacao_saude_profissionais",
        schema = "public",
        joinColumns = @JoinColumn(name = "educacao_saude_id"),
        inverseJoinColumns = @JoinColumn(name = "profissional_id")
    )
    private List<ProfissionaisSaude> profissionaisParticipantes = new ArrayList<>();

    // ========== TIPO DE ATIVIDADE ==========

    @Convert(converter = TipoEducacaoSaudeEnumConverter.class)
    @Column(name = "tipo_atividade", nullable = false)
    @NotNull(message = "Tipo de atividade é obrigatório")
    private TipoEducacaoSaudeEnum tipoAtividade;

    // ========== INFORMAÇÕES DA ATIVIDADE ==========

    @Size(max = 255, message = "Título deve ter no máximo 255 caracteres")
    @Column(name = "titulo", nullable = false, length = 255)
    @NotNull(message = "Título é obrigatório")
    private String titulo;

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    @Size(max = 255, message = "Tema deve ter no máximo 255 caracteres")
    @Column(name = "tema", length = 255)
    private String tema;

    @Column(name = "objetivos", columnDefinition = "TEXT")
    private String objetivos;

    @Column(name = "metodologia", columnDefinition = "TEXT")
    private String metodologia;

    @Column(name = "recursos_utilizados", columnDefinition = "TEXT")
    private String recursosUtilizados;

    // ========== LOCAL E HORÁRIO ==========

    @Column(name = "data_hora_inicio", nullable = false)
    @NotNull(message = "Data e hora de início são obrigatórios")
    private OffsetDateTime dataHoraInicio;

    @Column(name = "data_hora_fim")
    private OffsetDateTime dataHoraFim;

    @Column(name = "duracao_minutos")
    @Min(value = 0, message = "Duração não pode ser negativa")
    private Integer duracaoMinutos;

    @Size(max = 255, message = "Local deve ter no máximo 255 caracteres")
    @Column(name = "local", length = 255)
    private String local;

    @Size(max = 500, message = "Endereço deve ter no máximo 500 caracteres")
    @Column(name = "endereco", length = 500)
    private String endereco;

    // ========== PÚBLICO-ALVO ==========

    @Size(max = 255, message = "Público-alvo deve ter no máximo 255 caracteres")
    @Column(name = "publico_alvo", length = 255)
    private String publicoAlvo;

    @Column(name = "faixa_etaria_inicio")
    private Integer faixaEtariaInicio;

    @Column(name = "faixa_etaria_fim")
    private Integer faixaEtariaFim;

    // ========== PARTICIPAÇÃO ==========

    @Column(name = "numero_participantes_previsto")
    @Min(value = 0, message = "Número previsto não pode ser negativo")
    private Integer numeroParticipantesPrevisto;

    @Column(name = "numero_participantes_presente")
    @Min(value = 0, message = "Número de presentes não pode ser negativo")
    private Integer numeroParticipantesPresente;

    @Column(name = "lista_presenca_externa", columnDefinition = "TEXT")
    private String listaPresencaExterna; // Para participantes não cadastrados

    // ========== AVALIAÇÃO ==========

    @Column(name = "atividade_realizada", nullable = false)
    private Boolean atividadeRealizada = false;

    @Column(name = "avaliacao", columnDefinition = "TEXT")
    private String avaliacao;

    @Column(name = "pontos_positivos", columnDefinition = "TEXT")
    private String pontosPositivos;

    @Column(name = "pontos_melhoria", columnDefinition = "TEXT")
    private String pontosMelhoria;

    @Column(name = "encaminhamentos_realizados", columnDefinition = "TEXT")
    private String encaminhamentosRealizados;

    // ========== MATERIAL DE APOIO ==========

    @Column(name = "material_distribuido", columnDefinition = "TEXT")
    private String materialDistribuido;

    @Column(name = "quantidade_material_distribuido")
    @Min(value = 0, message = "Quantidade não pode ser negativa")
    private Integer quantidadeMaterialDistribuido;

    // ========== OBSERVAÇÕES ==========

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;
}

