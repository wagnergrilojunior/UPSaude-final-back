package com.upsaude.entity;

import com.upsaude.enums.StatusCirurgiaEnum;
import com.upsaude.util.converter.StatusCirurgiaEnumConverter;
import jakarta.persistence.CascadeType;
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

    /**
     * Construtor padrão que inicializa as coleções para evitar NullPointerException.
     */
    public Cirurgia() {
        this.procedimentos = new ArrayList<>();
        this.equipe = new ArrayList<>();
    }

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

    /**
     * Procedimentos realizados na cirurgia.
     * OneToMany bidirecional com cascade completo - JPA gerencia automaticamente.
     */
    @OneToMany(mappedBy = "cirurgia", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProcedimentoCirurgico> procedimentos = new ArrayList<>();

    /**
     * Equipe cirúrgica que participou da cirurgia.
     * OneToMany bidirecional com cascade completo - JPA gerencia automaticamente.
     */
    @OneToMany(mappedBy = "cirurgia", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
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

    // ========== MÉTODOS DE CICLO DE VIDA ==========

    /**
     * Garante que as coleções não sejam nulas antes de persistir ou atualizar.
     * Recria as listas se estiverem nulas.
     */
    @PrePersist
    @PreUpdate
    public void validateCollections() {
        if (procedimentos == null) {
            procedimentos = new ArrayList<>();
        }
        if (equipe == null) {
            equipe = new ArrayList<>();
        }
    }

    // ========== MÉTODOS UTILITÁRIOS - PROCEDIMENTOS ==========

    /**
     * Adiciona um procedimento à cirurgia com sincronização bidirecional.
     * Garante que o procedimento também referencia esta cirurgia.
     *
     * @param procedimento O procedimento a ser adicionado
     */
    public void addProcedimento(ProcedimentoCirurgico procedimento) {
        if (procedimento == null) {
            return;
        }
        if (procedimentos == null) {
            procedimentos = new ArrayList<>();
        }
        if (!procedimentos.contains(procedimento)) {
            procedimentos.add(procedimento);
            procedimento.setCirurgia(this);
        }
    }

    /**
     * Remove um procedimento da cirurgia com sincronização bidirecional.
     * Remove a referência do procedimento para esta cirurgia.
     *
     * @param procedimento O procedimento a ser removido
     */
    public void removeProcedimento(ProcedimentoCirurgico procedimento) {
        if (procedimento == null || procedimentos == null) {
            return;
        }
        if (procedimentos.remove(procedimento)) {
            procedimento.setCirurgia(null);
        }
    }

    // ========== MÉTODOS UTILITÁRIOS - EQUIPE ==========

    /**
     * Adiciona um membro à equipe cirúrgica com sincronização bidirecional.
     * Garante que o membro da equipe também referencia esta cirurgia.
     *
     * @param membroEquipe O membro da equipe a ser adicionado
     */
    public void addMembroEquipe(EquipeCirurgica membroEquipe) {
        if (membroEquipe == null) {
            return;
        }
        if (equipe == null) {
            equipe = new ArrayList<>();
        }
        if (!equipe.contains(membroEquipe)) {
            equipe.add(membroEquipe);
            membroEquipe.setCirurgia(this);
        }
    }

    /**
     * Remove um membro da equipe cirúrgica com sincronização bidirecional.
     * Remove a referência do membro para esta cirurgia.
     *
     * @param membroEquipe O membro da equipe a ser removido
     */
    public void removeMembroEquipe(EquipeCirurgica membroEquipe) {
        if (membroEquipe == null || equipe == null) {
            return;
        }
        if (equipe.remove(membroEquipe)) {
            membroEquipe.setCirurgia(null);
        }
    }
}

