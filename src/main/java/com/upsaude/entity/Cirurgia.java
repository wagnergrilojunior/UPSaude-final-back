package com.upsaude.entity;

import com.upsaude.enums.StatusCirurgiaEnum;
import com.upsaude.util.converter.StatusCirurgiaEnumConverter;
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

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidade que representa uma cirurgia realizada no estabelecimento.
 * Armazena informações completas sobre cirurgias para sistemas de gestão cirúrgica.
 *
 * @author UPSaúde
 */
@Entity
@Table(name = "cirurgias", schema = "public",
       indexes = {
           @Index(name = "idx_cirurgia_paciente", columnList = "paciente_id"),
           @Index(name = "idx_cirurgia_cirurgiao", columnList = "cirurgiao_principal_id"),
           @Index(name = "idx_cirurgia_data_hora", columnList = "data_hora_prevista"),
           @Index(name = "idx_cirurgia_status", columnList = "status"),
           @Index(name = "idx_cirurgia_estabelecimento", columnList = "estabelecimento_id"),
           @Index(name = "idx_cirurgia_sala", columnList = "sala_cirurgica")
       })
@Data
@EqualsAndHashCode(callSuper = true)
public class Cirurgia extends BaseEntity {

    // ========== RELACIONAMENTOS ==========

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    @NotNull(message = "Paciente é obrigatório")
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cirurgiao_principal_id", nullable = false)
    @NotNull(message = "Cirurgião principal é obrigatório")
    private ProfissionaisSaude cirurgiaoPrincipal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_cirurgiao_id")
    private Medicos medicoCirurgiao; // Opcional: médico específico como cirurgião

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "especialidade_id")
    private EspecialidadesMedicas especialidade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "convenio_id")
    private Convenio convenio;

    // ========== RELACIONAMENTOS FILHOS ==========

    @OneToMany(mappedBy = "cirurgia", fetch = FetchType.LAZY)
    private List<ProcedimentoCirurgico> procedimentos = new ArrayList<>();

    @OneToMany(mappedBy = "cirurgia", fetch = FetchType.LAZY)
    private List<EquipeCirurgica> equipe = new ArrayList<>();

    // ========== DADOS BÁSICOS ==========

    @Column(name = "descricao", nullable = false, columnDefinition = "TEXT")
    @NotNull(message = "Descrição da cirurgia é obrigatória")
    private String descricao;

    @Column(name = "codigo_procedimento", length = 50)
    private String codigoProcedimento; // Código do procedimento (ex: TUSS, SUS)

    // ========== AGENDAMENTO ==========

    @Column(name = "data_hora_prevista", nullable = false)
    @NotNull(message = "Data e hora prevista são obrigatórias")
    private OffsetDateTime dataHoraPrevista;

    @Column(name = "data_hora_inicio")
    private OffsetDateTime dataHoraInicio; // Hora real de início

    @Column(name = "data_hora_fim")
    private OffsetDateTime dataHoraFim; // Hora real de fim

    @Column(name = "duracao_prevista_minutos")
    private Integer duracaoPrevistaMinutos; // Duração prevista em minutos

    @Column(name = "duracao_real_minutos")
    private Integer duracaoRealMinutos; // Duração real em minutos

    // ========== LOCALIZAÇÃO ==========

    @Column(name = "sala_cirurgica", length = 100)
    private String salaCirurgica; // Sala ou centro cirúrgico

    @Column(name = "leito_centro_cirurgico", length = 50)
    private String leitoCentroCirurgico;

    // ========== STATUS ==========

    @Convert(converter = StatusCirurgiaEnumConverter.class)
    @Column(name = "status", nullable = false)
    @NotNull(message = "Status da cirurgia é obrigatório")
    private StatusCirurgiaEnum status;

    // ========== VALORES E FINANCEIRO ==========

    @Column(name = "valor_cirurgia", precision = 10, scale = 2)
    private BigDecimal valorCirurgia;

    @Column(name = "valor_material", precision = 10, scale = 2)
    private BigDecimal valorMaterial; // Valor dos materiais utilizados

    @Column(name = "valor_total", precision = 10, scale = 2)
    private BigDecimal valorTotal; // Valor total (cirurgia + material + outros)

    // ========== OBSERVAÇÕES E NOTAS ==========

    @Column(name = "observacoes_pre_operatorio", columnDefinition = "TEXT")
    private String observacoesPreOperatorio;

    @Column(name = "observacoes_pos_operatorio", columnDefinition = "TEXT")
    private String observacoesPosOperatorio;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    @Column(name = "observacoes_internas", columnDefinition = "TEXT")
    private String observacoesInternas; // Observações internas (não visíveis ao paciente)
}

