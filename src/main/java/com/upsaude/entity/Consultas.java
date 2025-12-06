package com.upsaude.entity;

import com.upsaude.entity.embeddable.AnamneseConsulta;
import com.upsaude.entity.embeddable.AtestadoConsulta;
import com.upsaude.entity.embeddable.DiagnosticoConsulta;
import com.upsaude.entity.embeddable.EncaminhamentoConsulta;
import com.upsaude.entity.embeddable.ExamesSolicitadosConsulta;
import com.upsaude.entity.embeddable.InformacoesConsulta;
import com.upsaude.entity.embeddable.PrescricaoConsulta;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Entidade que representa uma consulta médica.
 * Armazena informações completas sobre consultas para sistemas de gestão de saúde.
 * Baseado em padrões de prontuário eletrônico e sistemas de saúde.
 *
 * @author UPSaúde
 */
@Entity
@Table(name = "consultas", schema = "public",
       indexes = {
           @Index(name = "idx_consulta_paciente", columnList = "paciente_id"),
           @Index(name = "idx_consulta_medico", columnList = "medico_id"),
           @Index(name = "idx_consulta_data", columnList = "data_consulta"),
           @Index(name = "idx_consulta_status", columnList = "status_consulta"),
           @Index(name = "idx_consulta_estabelecimento", columnList = "estabelecimento_id")
       })
@Data
@EqualsAndHashCode(callSuper = true)
public class Consultas extends BaseEntity {

    public Consultas() {
        this.informacoes = new InformacoesConsulta();
        this.anamnese = new AnamneseConsulta();
        this.diagnostico = new DiagnosticoConsulta();
        this.prescricao = new PrescricaoConsulta();
        this.examesSolicitados = new ExamesSolicitadosConsulta();
        this.encaminhamento = new EncaminhamentoConsulta();
        this.atestado = new AtestadoConsulta();
    }

    // ========== RELACIONAMENTOS ==========

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    @NotNull(message = "Paciente é obrigatório")
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id")
    private Medicos medico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profissional_saude_id")
    private ProfissionaisSaude profissionalSaude; // Profissional que realizou a consulta (pode ser diferente do médico)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "especialidade_id")
    private EspecialidadesMedicas especialidade; // Especialidade da consulta

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "convenio_id")
    private Convenio convenio; // Convênio utilizado na consulta

    // ========== INFORMAÇÕES BÁSICAS ==========

    @Embedded
    private InformacoesConsulta informacoes;

    // ========== ANAMNESE ==========

    @Embedded
    private AnamneseConsulta anamnese;

    // ========== DIAGNÓSTICO ==========

    @Embedded
    private DiagnosticoConsulta diagnostico;

    // ========== PRESCRIÇÃO ==========

    @Embedded
    private PrescricaoConsulta prescricao;

    // ========== EXAMES SOLICITADOS ==========

    @Embedded
    private ExamesSolicitadosConsulta examesSolicitados;

    // ========== ENCAMINHAMENTO ==========

    @Embedded
    private EncaminhamentoConsulta encaminhamento;

    // ========== ATESTADO ==========

    @Embedded
    private AtestadoConsulta atestado;

    // ========== RELACIONAMENTO COM CID ==========

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cid_principal_id")
    private CidDoencas cidPrincipal; // CID principal da consulta

    // ========== OBSERVAÇÕES ==========

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes; // Observações gerais da consulta

    @Column(name = "observacoes_internas", columnDefinition = "TEXT")
    private String observacoesInternas; // Observações internas (não visíveis ao paciente)

    @PrePersist
    @PreUpdate
    public void validateEmbeddables() {
        if (informacoes == null) {
            informacoes = new InformacoesConsulta();
        }
        if (anamnese == null) {
            anamnese = new AnamneseConsulta();
        }
        if (diagnostico == null) {
            diagnostico = new DiagnosticoConsulta();
        }
        if (prescricao == null) {
            prescricao = new PrescricaoConsulta();
        }
        if (examesSolicitados == null) {
            examesSolicitados = new ExamesSolicitadosConsulta();
        }
        if (encaminhamento == null) {
            encaminhamento = new EncaminhamentoConsulta();
        }
        if (atestado == null) {
            atestado = new AtestadoConsulta();
        }
    }
}
