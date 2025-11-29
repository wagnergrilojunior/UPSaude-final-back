package com.upsaude.entity;

import com.upsaude.enums.PrioridadeAtendimentoEnum;
import com.upsaude.enums.StatusAgendamentoEnum;
import com.upsaude.util.converter.PrioridadeAtendimentoEnumConverter;
import com.upsaude.util.converter.StatusAgendamentoEnumConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidade que representa um agendamento de atendimento.
 * Armazena informações completas sobre agendamentos com controle de conflitos e fila de espera.
 *
 * @author UPSaúde
 */
@Entity
@Table(name = "agendamentos", schema = "public",
       indexes = {
           @Index(name = "idx_agendamento_paciente", columnList = "paciente_id"),
           @Index(name = "idx_agendamento_profissional", columnList = "profissional_id"),
           @Index(name = "idx_agendamento_data_hora", columnList = "data_hora"),
           @Index(name = "idx_agendamento_status", columnList = "status"),
           @Index(name = "idx_agendamento_estabelecimento", columnList = "estabelecimento_id"),
           @Index(name = "idx_agendamento_prioridade", columnList = "prioridade"),
           @Index(name = "idx_agendamento_periodo", columnList = "data_hora,data_hora_fim"),
           @Index(name = "idx_agendamento_agendado_por", columnList = "agendado_por")
       })
@Data
@EqualsAndHashCode(callSuper = true)
public class Agendamento extends BaseEntity {

    // ========== RELACIONAMENTOS ==========

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    @NotNull(message = "Paciente é obrigatório")
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profissional_id", nullable = false)
    @NotNull(message = "Profissional de saúde é obrigatório")
    private ProfissionaisSaude profissional;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id")
    private Medicos medico; // Opcional: médico específico

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "especialidade_id")
    private EspecialidadesMedicas especialidade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "convenio_id")
    private Convenio convenio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "atendimento_id")
    private Atendimento atendimento; // Link para o atendimento quando for realizado

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agendamento_original_id")
    private Agendamento agendamentoOriginal; // Para rastrear reagendamentos

    // ========== RELACIONAMENTOS FILHOS ==========

    @OneToMany(mappedBy = "agendamentoOriginal", fetch = FetchType.LAZY)
    private List<Agendamento> reagendamentos = new ArrayList<>(); // Reagendamentos feitos a partir deste

    @OneToMany(mappedBy = "agendamento", fetch = FetchType.LAZY)
    private List<Notificacao> notificacoes = new ArrayList<>(); // Notificações relacionadas

    @OneToMany(mappedBy = "agendamento", fetch = FetchType.LAZY)
    private List<CheckInAtendimento> checkIns = new ArrayList<>(); // Check-ins relacionados

    // ========== DADOS DO AGENDAMENTO ==========

    @Column(name = "data_hora", nullable = false)
    @NotNull(message = "Data e hora do agendamento são obrigatórias")
    private OffsetDateTime dataHora;

    @Column(name = "data_hora_fim")
    private OffsetDateTime dataHoraFim; // Data/hora prevista de fim

    @Column(name = "duracao_prevista_minutos")
    private Integer duracaoPrevistaMinutos; // Duração prevista em minutos

    @Convert(converter = StatusAgendamentoEnumConverter.class)
    @Column(name = "status", nullable = false)
    @NotNull(message = "Status do agendamento é obrigatório")
    private StatusAgendamentoEnum status;

    @Convert(converter = PrioridadeAtendimentoEnumConverter.class)
    @Column(name = "prioridade")
    private PrioridadeAtendimentoEnum prioridade;

    @Column(name = "eh_encaixe")
    private Boolean ehEncaixe; // Se é um encaixe

    @Column(name = "eh_retorno")
    private Boolean ehRetorno; // Se é um retorno

    // ========== INFORMAÇÕES DE AGENDAMENTO ==========

    @Column(name = "motivo_consulta", columnDefinition = "TEXT")
    private String motivoConsulta; // Motivo da consulta informado pelo paciente

    @Column(name = "observacoes_agendamento", columnDefinition = "TEXT")
    private String observacoesAgendamento; // Observações do agendamento

    @Column(name = "observacoes_internas", columnDefinition = "TEXT")
    private String observacoesInternas; // Observações internas

    // ========== CONTROLE DE CONFLITOS ==========

    @Column(name = "tem_conflito_horario")
    private Boolean temConflitoHorario; // Se tem conflito de horário detectado

    @Column(name = "sobreposicao_permitida")
    private Boolean sobreposicaoPermitida; // Se sobreposição foi permitida

    @Column(name = "justificativa_conflito", columnDefinition = "TEXT")
    private String justificativaConflito; // Justificativa para conflito permitido

    // ========== CONTROLE DE CANCELAMENTO/REAGENDAMENTO ==========

    @Column(name = "data_cancelamento")
    private OffsetDateTime dataCancelamento;

    @Column(name = "cancelado_por")
    private java.util.UUID canceladoPor; // ID do usuário que cancelou

    @Column(name = "motivo_cancelamento", columnDefinition = "TEXT")
    private String motivoCancelamento;

    @Column(name = "data_reagendamento")
    private OffsetDateTime dataReagendamento;

    @Column(name = "reagendado_por")
    private java.util.UUID reagendadoPor; // ID do usuário que reagendou

    @Column(name = "motivo_reagendamento", columnDefinition = "TEXT")
    private String motivoReagendamento;

    // ========== AUDITORIA ==========

    @Column(name = "agendado_por")
    private java.util.UUID agendadoPor; // ID do usuário que criou o agendamento

    @Column(name = "confirmado_por")
    private java.util.UUID confirmadoPor; // ID do usuário que confirmou

    @Column(name = "data_confirmacao")
    private OffsetDateTime dataConfirmacao;

    @Column(name = "data_ultima_alteracao")
    private OffsetDateTime dataUltimaAlteracao;

    @Column(name = "alterado_por")
    private java.util.UUID alteradoPor; // ID do usuário da última alteração

    // ========== NOTIFICAÇÕES ==========

    @Column(name = "notificacao_enviada_24h")
    private Boolean notificacaoEnviada24h; // Se notificação 24h foi enviada

    @Column(name = "notificacao_enviada_1h")
    private Boolean notificacaoEnviada1h; // Se notificação 1h foi enviada

    @Column(name = "confirmacao_enviada")
    private Boolean confirmacaoEnviada; // Se confirmação foi enviada
}

