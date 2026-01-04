package com.upsaude.entity.agendamento;
import com.upsaude.entity.profissional.Medicos;
import com.upsaude.entity.BaseEntity;

import com.upsaude.entity.profissional.ProfissionaisSaude;

import com.upsaude.entity.agendamento.Agendamento;

import com.upsaude.entity.paciente.Paciente;

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
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.OffsetDateTime;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profissional_id")
    private ProfissionaisSaude profissional;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id")
    private Medicos medico;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agendamento_id")
    private Agendamento agendamento;

    @Column(name = "data_entrada", nullable = false)
    private OffsetDateTime dataEntrada;

    @Column(name = "data_fim_desejada")
    private java.time.LocalDate dataFimDesejada;

    @Convert(converter = PrioridadeAtendimentoEnumConverter.class)
    @Column(name = "prioridade")
    private PrioridadeAtendimentoEnum prioridade;

    @Column(name = "posicao_fila")
    private Integer posicaoFila;

    @Column(name = "motivo", columnDefinition = "TEXT")
    private String motivo;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    @Column(name = "notificado")
    private Boolean notificado;

    @Column(name = "data_notificacao")
    private OffsetDateTime dataNotificacao;

    @Column(name = "notificacoes_enviadas")
    private Integer notificacoesEnviadas;

    @Column(name = "aceita_qualquer_horario")
    private Boolean aceitaQualquerHorario;

    @Column(name = "telefone_contato", length = 20)
    private String telefoneContato;

    @Column(name = "email_contato", length = 255)
    private String emailContato;
}
