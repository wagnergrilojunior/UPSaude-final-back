package com.upsaude.entity.profissional;
import com.upsaude.entity.profissional.Medicos;
import com.upsaude.entity.BaseEntity;

import com.upsaude.entity.profissional.ProfissionaisSaude;

import com.upsaude.entity.clinica.atendimento.Atendimento;

import com.upsaude.entity.paciente.Paciente;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profissional_id", nullable = false)
    @NotNull(message = "Profissional de saúde é obrigatório")
    private ProfissionaisSaude profissional;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id")
    private Medicos medico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "atendimento_id")
    private Atendimento atendimento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cirurgia_id")
    private Cirurgia cirurgia;

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

    @Column(name = "duracao_minutos")
    private Integer duracaoMinutos;

    @Column(name = "data_hora_inicio")
    private OffsetDateTime dataHoraInicio;

    @Column(name = "data_hora_fim")
    private OffsetDateTime dataHoraFim;

    @Column(name = "local_realizacao", length = 255)
    private String localRealizacao;

    @Column(name = "setor", length = 255)
    private String setor;

    @Column(name = "quantidade_atendimentos")
    private Integer quantidadeAtendimentos;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    @Column(name = "observacoes_internas", columnDefinition = "TEXT")
    private String observacoesInternas;
}
