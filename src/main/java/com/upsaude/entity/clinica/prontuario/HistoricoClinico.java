package com.upsaude.entity.clinica.prontuario;
import com.upsaude.entity.BaseEntity;

import com.upsaude.entity.profissional.ProfissionaisSaude;

import com.upsaude.entity.agendamento.Agendamento;

import com.upsaude.entity.clinica.atendimento.Atendimento;

import com.upsaude.entity.paciente.Paciente;

import jakarta.persistence.Column;
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
@Table(name = "historico_clinico", schema = "public",
       indexes = {
           @Index(name = "idx_historico_paciente", columnList = "paciente_id"),
           @Index(name = "idx_historico_data", columnList = "data_registro"),
           @Index(name = "idx_historico_tipo", columnList = "tipo_registro"),
           @Index(name = "idx_historico_estabelecimento", columnList = "estabelecimento_id"),
           @Index(name = "idx_historico_profissional", columnList = "profissional_id")
       })
@Data
@EqualsAndHashCode(callSuper = true)
public class HistoricoClinico extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    @NotNull(message = "Paciente é obrigatório")
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profissional_id")
    private ProfissionaisSaude profissional;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "atendimento_id")
    private Atendimento atendimento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agendamento_id")
    private Agendamento agendamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exame_id")
    private Exames exame;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receita_id")
    private ReceitasMedicas receita;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cirurgia_id")
    private Cirurgia cirurgia;

    @Column(name = "data_registro", nullable = false)
    @NotNull(message = "Data do registro é obrigatória")
    private OffsetDateTime dataRegistro;

    @Column(name = "tipo_registro", nullable = false, length = 50)
    @NotNull(message = "Tipo de registro é obrigatório")
    private String tipoRegistro;

    @Column(name = "titulo", length = 255)
    private String titulo;

    @Column(name = "descricao", nullable = false, columnDefinition = "TEXT")
    @NotNull(message = "Descrição é obrigatória")
    private String descricao;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    @Column(name = "observacoes_internas", columnDefinition = "TEXT")
    private String observacoesInternas;

    @Column(name = "tags", length = 500)
    private String tags;

    @Column(name = "registrado_por")
    private java.util.UUID registradoPor;

    @Column(name = "revisado_por")
    private java.util.UUID revisadoPor;

    @Column(name = "data_revisao")
    private OffsetDateTime dataRevisao;

    @Column(name = "versao")
    private Integer versao;

    @Column(name = "visivel_para_paciente")
    private Boolean visivelParaPaciente;

    @Column(name = "compartilhado_outros_estabelecimentos")
    private Boolean compartilhadoOutrosEstabelecimentos;
}
