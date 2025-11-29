package com.upsaude.entity;

import com.upsaude.enums.PrioridadeAtendimentoEnum;
import com.upsaude.util.converter.PrioridadeAtendimentoEnumConverter;
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
 * Entidade que representa uma entrada na fila de espera.
 * Permite que pacientes sejam adicionados à fila quando não há horários disponíveis.
 *
 * @author UPSaúde
 */
@Entity
@Table(name = "fila_espera", schema = "public",
       indexes = {
           @Index(name = "idx_fila_espera_paciente", columnList = "paciente_id"),
           @Index(name = "idx_fila_espera_profissional", columnList = "profissional_id"),
           @Index(name = "idx_fila_espera_data_entrada", columnList = "data_entrada"),
           @Index(name = "idx_fila_espera_prioridade", columnList = "prioridade"),
           @Index(name = "idx_fila_espera_estabelecimento", columnList = "estabelecimento_id"),
           @Index(name = "idx_fila_espera_status", columnList = "ativo")
       })
@Data
@EqualsAndHashCode(callSuper = true)
public class FilaEspera extends BaseEntity {

    // ========== RELACIONAMENTOS ==========

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    @NotNull(message = "Paciente é obrigatório")
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profissional_id")
    private ProfissionaisSaude profissional; // Opcional: profissional específico

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id")
    private Medicos medico; // Opcional: médico específico

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "especialidade_id")
    private EspecialidadesMedicas especialidade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agendamento_id")
    private Agendamento agendamento; // Agendamento criado quando vaga disponível

    // ========== DADOS DA FILA ==========

    @Column(name = "data_entrada", nullable = false)
    @NotNull(message = "Data de entrada na fila é obrigatória")
    private OffsetDateTime dataEntrada;

    @Column(name = "data_fim_desejada")
    private java.time.LocalDate dataFimDesejada; // Até quando o paciente deseja ser atendido

    @Convert(converter = PrioridadeAtendimentoEnumConverter.class)
    @Column(name = "prioridade")
    private PrioridadeAtendimentoEnum prioridade;

    @Column(name = "posicao_fila")
    private Integer posicaoFila; // Posição na fila

    // ========== INFORMAÇÕES ==========

    @Column(name = "motivo", columnDefinition = "TEXT")
    private String motivo; // Motivo para estar na fila

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    // ========== NOTIFICAÇÕES ==========

    @Column(name = "notificado")
    private Boolean notificado; // Se foi notificado sobre vaga disponível

    @Column(name = "data_notificacao")
    private OffsetDateTime dataNotificacao;

    @Column(name = "notificacoes_enviadas")
    private Integer notificacoesEnviadas; // Quantidade de notificações enviadas

    // ========== CONTROLE ==========

    @Column(name = "aceita_qualquer_horario")
    private Boolean aceitaQualquerHorario; // Se aceita qualquer horário disponível

    @Column(name = "telefone_contato", length = 20)
    private String telefoneContato; // Telefone para contato rápido

    @Column(name = "email_contato", length = 255)
    private String emailContato; // E-mail para contato
}

