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

/**
 * Entidade que representa o relacionamento entre um paciente e uma doença.
 * Armazena informações completas sobre o diagnóstico, acompanhamento e tratamento
 * de uma doença específica em um paciente.
 *
 * @author UPSaúde
 */
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

    // ========== RELACIONAMENTOS ==========

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
    private CidDoencas cidPrincipal; // CID específico para este paciente

    // ========== DIAGNÓSTICO ==========

    @Embedded
    private DiagnosticoDoencaPaciente diagnostico;

    // ========== ACOMPANHAMENTO ==========

    @Embedded
    private AcompanhamentoDoencaPaciente acompanhamento;

    // ========== TRATAMENTO ATUAL ==========

    @Embedded
    private TratamentoAtualDoencaPaciente tratamentoAtual;

    // ========== OBSERVAÇÕES ==========

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes; // Observações específicas sobre a doença neste paciente

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

