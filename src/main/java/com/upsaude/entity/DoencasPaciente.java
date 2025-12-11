package com.upsaude.entity;

import com.upsaude.entity.embeddable.AcompanhamentoDoencaPaciente;
import com.upsaude.entity.embeddable.DiagnosticoDoencaPaciente;
import com.upsaude.entity.embeddable.TratamentoAtualDoencaPaciente;
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

@Entity
@Table(name = "doencas_paciente", schema = "public",
       indexes = {
           @Index(name = "idx_doenca_paciente_paciente", columnList = "paciente_id"),
           @Index(name = "idx_doenca_paciente_doenca", columnList = "doenca_id"),
           @Index(name = "idx_doenca_paciente_cid", columnList = "cid_principal_id"),
           @Index(name = "idx_doenca_paciente_data_diagnostico", columnList = "data_diagnostico")
       })
@Data
@EqualsAndHashCode(callSuper = true)
public class DoencasPaciente extends BaseEntity {

    public DoencasPaciente() {
        this.diagnostico = new DiagnosticoDoencaPaciente();
        this.acompanhamento = new AcompanhamentoDoencaPaciente();
        this.tratamentoAtual = new TratamentoAtualDoencaPaciente();
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    @NotNull(message = "Paciente é obrigatório")
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doenca_id", nullable = false)
    @NotNull(message = "Doença é obrigatória")
    private Doencas doenca;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cid_principal_id")
    private CidDoencas cidPrincipal;

    @Embedded
    private DiagnosticoDoencaPaciente diagnostico;

    @Embedded
    private AcompanhamentoDoencaPaciente acompanhamento;

    @Embedded
    private TratamentoAtualDoencaPaciente tratamentoAtual;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    @PrePersist
    @PreUpdate
    public void validateEmbeddables() {
        if (diagnostico == null) {
            diagnostico = new DiagnosticoDoencaPaciente();
        }
        if (acompanhamento == null) {
            acompanhamento = new AcompanhamentoDoencaPaciente();
        }
        if (tratamentoAtual == null) {
            tratamentoAtual = new TratamentoAtualDoencaPaciente();
        }
    }
}
