package com.upsaude.entity.agendamento;
import com.upsaude.entity.sistema.notificacao.Notificacao;
import com.upsaude.entity.profissional.EspecialidadesMedicas;
import com.upsaude.entity.profissional.Medicos;
import com.upsaude.entity.clinica.atendimento.CheckInAtendimento;
import com.upsaude.entity.BaseEntity;

import com.upsaude.entity.profissional.ProfissionaisSaude;
import com.upsaude.entity.convenio.Convenio;

import com.upsaude.entity.clinica.atendimento.Atendimento;

import com.upsaude.entity.paciente.Paciente;

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
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

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

    public Agendamento() {
        this.reagendamentos = new ArrayList<>();
        this.notificacoes = new ArrayList<>();
        this.checkIns = new ArrayList<>();
    }

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
    private Medicos medico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "especialidade_id")
    private EspecialidadesMedicas especialidade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "convenio_id")
    private Convenio convenio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "atendimento_id")
    private Atendimento atendimento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agendamento_original_id")
    private Agendamento agendamentoOriginal;

    @OneToMany(mappedBy = "agendamentoOriginal", fetch = FetchType.LAZY)
    private List<Agendamento> reagendamentos = new ArrayList<>();

    @OneToMany(mappedBy = "agendamento", fetch = FetchType.LAZY)
    private List<Notificacao> notificacoes = new ArrayList<>();

    @OneToMany(mappedBy = "agendamento", fetch = FetchType.LAZY)
    private List<CheckInAtendimento> checkIns = new ArrayList<>();

    @Column(name = "data_hora", nullable = false)
    @NotNull(message = "Data e hora do agendamento são obrigatórias")
    private OffsetDateTime dataHora;

    @Column(name = "data_hora_fim")
    private OffsetDateTime dataHoraFim;

    @Column(name = "duracao_prevista_minutos")
    private Integer duracaoPrevistaMinutos;

    @Convert(converter = StatusAgendamentoEnumConverter.class)
    @Column(name = "status", nullable = false)
    @NotNull(message = "Status do agendamento é obrigatório")
    private StatusAgendamentoEnum status;

    @Convert(converter = PrioridadeAtendimentoEnumConverter.class)
    @Column(name = "prioridade")
    private PrioridadeAtendimentoEnum prioridade;

    @Column(name = "eh_encaixe")
    private Boolean ehEncaixe;

    @Column(name = "eh_retorno")
    private Boolean ehRetorno;

    @Column(name = "motivo_consulta", columnDefinition = "TEXT")
    private String motivoConsulta;

    @Column(name = "observacoes_agendamento", columnDefinition = "TEXT")
    private String observacoesAgendamento;

    @Column(name = "observacoes_internas", columnDefinition = "TEXT")
    private String observacoesInternas;

    @Column(name = "tem_conflito_horario")
    private Boolean temConflitoHorario;

    @Column(name = "sobreposicao_permitida")
    private Boolean sobreposicaoPermitida;

    @Column(name = "justificativa_conflito", columnDefinition = "TEXT")
    private String justificativaConflito;

    @Column(name = "data_cancelamento")
    private OffsetDateTime dataCancelamento;

    @Column(name = "cancelado_por")
    private java.util.UUID canceladoPor;

    @Column(name = "motivo_cancelamento", columnDefinition = "TEXT")
    private String motivoCancelamento;

    @Column(name = "data_reagendamento")
    private OffsetDateTime dataReagendamento;

    @Column(name = "reagendado_por")
    private java.util.UUID reagendadoPor;

    @Column(name = "motivo_reagendamento", columnDefinition = "TEXT")
    private String motivoReagendamento;

    @Column(name = "agendado_por")
    private java.util.UUID agendadoPor;

    @Column(name = "confirmado_por")
    private java.util.UUID confirmadoPor;

    @Column(name = "data_confirmacao")
    private OffsetDateTime dataConfirmacao;

    @Column(name = "data_ultima_alteracao")
    private OffsetDateTime dataUltimaAlteracao;

    @Column(name = "alterado_por")
    private java.util.UUID alteradoPor;

    @Column(name = "notificacao_enviada_24h")
    private Boolean notificacaoEnviada24h;

    @Column(name = "notificacao_enviada_1h")
    private Boolean notificacaoEnviada1h;

    @Column(name = "confirmacao_enviada")
    private Boolean confirmacaoEnviada;

    @PrePersist
    @PreUpdate
    public void validateCollections() {
        if (reagendamentos == null) {
            reagendamentos = new ArrayList<>();
        }
        if (notificacoes == null) {
            notificacoes = new ArrayList<>();
        }
        if (checkIns == null) {
            checkIns = new ArrayList<>();
        }
    }

    public void addReagendamento(Agendamento reagendamento) {
        if (reagendamento == null) {
            return;
        }
        if (reagendamentos == null) {
            reagendamentos = new ArrayList<>();
        }
        if (!reagendamentos.contains(reagendamento)) {
            reagendamentos.add(reagendamento);
            reagendamento.setAgendamentoOriginal(this);
        }
    }

    public void removeReagendamento(Agendamento reagendamento) {
        if (reagendamento == null || reagendamentos == null) {
            return;
        }
        if (reagendamentos.remove(reagendamento)) {
            reagendamento.setAgendamentoOriginal(null);
        }
    }

    public void addNotificacao(Notificacao notificacao) {
        if (notificacao == null) {
            return;
        }
        if (notificacoes == null) {
            notificacoes = new ArrayList<>();
        }
        if (!notificacoes.contains(notificacao)) {
            notificacoes.add(notificacao);
            notificacao.setAgendamento(this);
        }
    }

    public void removeNotificacao(Notificacao notificacao) {
        if (notificacao == null || notificacoes == null) {
            return;
        }
        if (notificacoes.remove(notificacao)) {
            notificacao.setAgendamento(null);
        }
    }

    public void addCheckIn(CheckInAtendimento checkIn) {
        if (checkIn == null) {
            return;
        }
        if (checkIns == null) {
            checkIns = new ArrayList<>();
        }
        if (!checkIns.contains(checkIn)) {
            checkIns.add(checkIn);
            checkIn.setAgendamento(this);
        }
    }

    public void removeCheckIn(CheckInAtendimento checkIn) {
        if (checkIn == null || checkIns == null) {
            return;
        }
        if (checkIns.remove(checkIn)) {
            checkIn.setAgendamento(null);
        }
    }
}
