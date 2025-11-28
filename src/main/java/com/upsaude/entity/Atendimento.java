package com.upsaude.entity;

import com.upsaude.entity.embeddable.AnamneseAtendimento;
import com.upsaude.entity.embeddable.ClassificacaoRiscoAtendimento;
import com.upsaude.entity.embeddable.DiagnosticoAtendimento;
import com.upsaude.entity.embeddable.InformacoesAtendimento;
import com.upsaude.entity.embeddable.ProcedimentosRealizadosAtendimento;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Entidade que representa um atendimento realizado por um profissional de saúde a um paciente.
 * Armazena informações completas sobre atendimentos para sistemas de gestão de saúde.
 * Baseado em padrões de prontuário eletrônico e sistemas de saúde.
 *
 * @author UPSaúde
 */
@Entity
@Table(name = "atendimentos", schema = "public",
       indexes = {
           @Index(name = "idx_atendimento_paciente", columnList = "paciente_id"),
           @Index(name = "idx_atendimento_profissional", columnList = "profissional_id"),
           @Index(name = "idx_atendimento_data_hora", columnList = "data_hora"),
           @Index(name = "idx_atendimento_tipo", columnList = "tipo_atendimento"),
           @Index(name = "idx_atendimento_status", columnList = "status_atendimento"),
           @Index(name = "idx_atendimento_estabelecimento", columnList = "estabelecimento_id")
       })
@Data
@EqualsAndHashCode(callSuper = true)
public class Atendimento extends BaseEntity {

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
    @JoinColumn(name = "especialidade_id")
    private EspecialidadesMedicas especialidade; // Especialidade do atendimento

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipe_saude_id")
    private EquipeSaude equipeSaude; // Equipe de saúde que realizou o atendimento

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "convenio_id")
    private Convenio convenio; // Convênio utilizado no atendimento

    // ========== INFORMAÇÕES BÁSICAS ==========

    @Embedded
    private InformacoesAtendimento informacoes;

    // ========== ANAMNESE ==========

    @Embedded
    private AnamneseAtendimento anamnese;

    // ========== DIAGNÓSTICO ==========

    @Embedded
    private DiagnosticoAtendimento diagnostico;

    // ========== PROCEDIMENTOS REALIZADOS ==========

    @Embedded
    private ProcedimentosRealizadosAtendimento procedimentosRealizados;

    // ========== CLASSIFICAÇÃO DE RISCO ==========

    @Embedded
    private ClassificacaoRiscoAtendimento classificacaoRisco;

    // ========== RELACIONAMENTO COM CID ==========

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cid_principal_id")
    private CidDoencas cidPrincipal; // CID principal do atendimento

    // ========== OBSERVAÇÕES ==========

    @Column(name = "anotacoes", columnDefinition = "TEXT")
    private String anotacoes; // Anotações gerais do atendimento

    @Column(name = "observacoes_internas", columnDefinition = "TEXT")
    private String observacoesInternas; // Observações internas (não visíveis ao paciente)
}
