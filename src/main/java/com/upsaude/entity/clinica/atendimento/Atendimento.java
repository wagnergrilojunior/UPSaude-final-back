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
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "atendimentos", schema = "public",
       indexes = {
           @Index(name = "idx_atendimento_paciente", columnList = "paciente_id"),
           @Index(name = "idx_atendimento_profissional", columnList = "profissional_id"),
           @Index(name = "idx_atendimento_data_hora", columnList = "data_hora"),
           @Index(name = "idx_atendimento_tipo", columnList = "tipo_atendimento"),
           @Index(name = "idx_atendimento_status", columnList = "status_atendimento"),
           @Index(name = "idx_atendimento_estabelecimento", columnList = "estabelecimento_id"),
           @Index(name = "idx_atendimento_consulta", columnList = "consulta_id")
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consulta_id", nullable = true)
    private Consultas consulta;

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
