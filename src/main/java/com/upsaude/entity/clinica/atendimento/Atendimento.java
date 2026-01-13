package com.upsaude.entity.clinica.atendimento;

import com.upsaude.entity.BaseEntity;

import com.upsaude.entity.profissional.ProfissionaisSaude;
import com.upsaude.entity.profissional.equipe.EquipeSaude;
import com.upsaude.entity.convenio.Convenio;

import com.upsaude.entity.paciente.Paciente;

import com.upsaude.entity.embeddable.AnamneseAtendimento;
import com.upsaude.entity.embeddable.ClassificacaoRiscoAtendimento;
import com.upsaude.entity.embeddable.DiagnosticoAtendimento;
import com.upsaude.entity.embeddable.InformacoesAtendimento;
import com.upsaude.entity.embeddable.ProcedimentosRealizadosAtendimento;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "atendimentos", schema = "public", indexes = {
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

    public Atendimento() {
        this.informacoes = new InformacoesAtendimento();
        this.anamnese = new AnamneseAtendimento();
        this.diagnostico = new DiagnosticoAtendimento();
        this.procedimentosRealizados = new ProcedimentosRealizadosAtendimento();
        this.classificacaoRisco = new ClassificacaoRiscoAtendimento();
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profissional_id", nullable = false)
    private ProfissionaisSaude profissional;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipe_saude_id")
    private EquipeSaude equipeSaude;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "convenio_id")
    private Convenio convenio;

    @Embedded
    private InformacoesAtendimento informacoes;

    @Embedded
    private AnamneseAtendimento anamnese;

    @Embedded
    private DiagnosticoAtendimento diagnostico;

    @Embedded
    private ProcedimentosRealizadosAtendimento procedimentosRealizados;

    @Embedded
    private ClassificacaoRiscoAtendimento classificacaoRisco;

    @Column(name = "anotacoes", columnDefinition = "TEXT")
    private String anotacoes;

    @Column(name = "observacoes_internas", columnDefinition = "TEXT")
    private String observacoesInternas;

    // =================================================================================
    // CAMPOS DE INTEGRAÇÃO SUS / RNDS (FHIR ENCOUNTER)
    // =================================================================================

    @Convert(converter = com.upsaude.util.converter.ClasseAtendimentoEnumConverter.class)
    @Column(name = "classe_atendimento", length = 50)
    private com.upsaude.enums.ClasseAtendimentoEnum classeAtendimento;

    @Column(name = "tipo_atendimento_detalhado", length = 100)
    private String tipoAtendimentoDetalhado;

    @Convert(converter = com.upsaude.util.converter.PrioridadeAtendimentoEnumConverter.class)
    @Column(name = "prioridade_atendimento", length = 20)
    private com.upsaude.enums.PrioridadeAtendimentoEnum prioridadeAtendimento;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "motivo_atendimento", columnDefinition = "jsonb")
    private String motivoAtendimento;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "diagnosticos_admissao", columnDefinition = "jsonb")
    private String diagnosticosAdmissao;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "dados_internacao", columnDefinition = "jsonb")
    private String dadosInternacao;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "periodo_real", columnDefinition = "jsonb")
    private String periodoReal;

    @OneToOne(mappedBy = "atendimento", fetch = FetchType.LAZY)
    private Consulta consulta;

    @PrePersist
    @PreUpdate
    public void validateEmbeddables() {
        if (informacoes == null) {
            informacoes = new InformacoesAtendimento();
        }
        if (anamnese == null) {
            anamnese = new AnamneseAtendimento();
        }
        if (diagnostico == null) {
            diagnostico = new DiagnosticoAtendimento();
        }
        if (procedimentosRealizados == null) {
            procedimentosRealizados = new ProcedimentosRealizadosAtendimento();
        }
        if (classificacaoRisco == null) {
            classificacaoRisco = new ClassificacaoRiscoAtendimento();
        }
    }
}
