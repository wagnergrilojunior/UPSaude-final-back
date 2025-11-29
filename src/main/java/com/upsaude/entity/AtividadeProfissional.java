package com.upsaude.entity;

import com.upsaude.enums.TipoAtividadeProfissionalEnum;
import com.upsaude.util.converter.TipoAtividadeProfissionalEnumConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.OffsetDateTime;

/**
 * Entidade que representa atividades realizadas por profissionais de saúde no dia a dia.
 * Permite registrar o trabalho diário dos profissionais para gestão e auditoria.
 *
 * @author UPSaúde
 */
@Entity
@Table(name = "atividades_profissionais", schema = "public",
       indexes = {
           @Index(name = "idx_atividade_profissional_profissional", columnList = "profissional_id"),
           @Index(name = "idx_atividade_profissional_data_hora", columnList = "data_hora"),
           @Index(name = "idx_atividade_profissional_tipo", columnList = "tipo_atividade"),
           @Index(name = "idx_atividade_profissional_estabelecimento", columnList = "estabelecimento_id"),
           @Index(name = "idx_atividade_profissional_data", columnList = "data_atividade")
       })
@Data
@EqualsAndHashCode(callSuper = true)
public class AtividadeProfissional extends BaseEntity {

    // ========== RELACIONAMENTOS ==========

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profissional_id", nullable = false)
    @NotNull(message = "Profissional de saúde é obrigatório")
    private ProfissionaisSaude profissional;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id")
    private Medicos medico; // Opcional: para médicos específicos

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id")
    private Paciente paciente; // Opcional: se a atividade envolve um paciente

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "atendimento_id")
    private Atendimento atendimento; // Opcional: se relacionado a um atendimento

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cirurgia_id")
    private Cirurgia cirurgia; // Opcional: se relacionado a uma cirurgia

    // ========== DADOS DA ATIVIDADE ==========

    @Column(name = "data_hora", nullable = false)
    @NotNull(message = "Data e hora da atividade são obrigatórias")
    private OffsetDateTime dataHora;

    @Column(name = "data_atividade", nullable = false)
    @NotNull(message = "Data da atividade é obrigatória")
    private java.time.LocalDate dataAtividade;

    @Convert(converter = TipoAtividadeProfissionalEnumConverter.class)
    @Column(name = "tipo_atividade", nullable = false)
    @NotNull(message = "Tipo de atividade é obrigatório")
    private TipoAtividadeProfissionalEnum tipoAtividade;

    @Column(name = "descricao", nullable = false, columnDefinition = "TEXT")
    @NotNull(message = "Descrição da atividade é obrigatória")
    private String descricao;

    // ========== DURAÇÃO ==========

    @Column(name = "duracao_minutos")
    private Integer duracaoMinutos; // Duração da atividade em minutos

    @Column(name = "data_hora_inicio")
    private OffsetDateTime dataHoraInicio; // Início da atividade (se diferente de data_hora)

    @Column(name = "data_hora_fim")
    private OffsetDateTime dataHoraFim; // Fim da atividade

    // ========== LOCALIZAÇÃO ==========

    @Column(name = "local_realizacao", length = 255)
    private String localRealizacao; // Local onde foi realizada a atividade

    @Column(name = "setor", length = 255)
    private String setor; // Setor/unidade

    // ========== INFORMAÇÕES COMPLEMENTARES ==========

    @Column(name = "quantidade_atendimentos")
    private Integer quantidadeAtendimentos; // Quantidade de atendimentos/procedimentos realizados

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    @Column(name = "observacoes_internas", columnDefinition = "TEXT")
    private String observacoesInternas; // Observações internas (não visíveis externamente)
}

