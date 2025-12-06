package com.upsaude.entity;

import com.upsaude.entity.embeddable.DiagnosticoAlergiaPaciente;
import com.upsaude.entity.embeddable.HistoricoReacoesAlergiaPaciente;
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
 * Entidade que representa o relacionamento entre um paciente e uma alergia.
 * Armazena informações completas sobre alergias específicas de um paciente.
 *
 * @author UPSaúde
 */
@Entity
@Table(name = "alergias_paciente", schema = "public",
       indexes = {
           @Index(name = "idx_alergia_paciente_paciente", columnList = "paciente_id"),
           @Index(name = "idx_alergia_paciente_alergia", columnList = "alergia_id"),
           @Index(name = "idx_alergia_paciente_data_diagnostico", columnList = "data_diagnostico")
       })
@Data
@EqualsAndHashCode(callSuper = true)
public class AlergiasPaciente extends BaseEntity {

    public AlergiasPaciente() {
        this.diagnostico = new DiagnosticoAlergiaPaciente();
        this.historicoReacoes = new HistoricoReacoesAlergiaPaciente();
    }

    // ========== RELACIONAMENTOS ==========

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    @NotNull(message = "Paciente é obrigatório")
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alergia_id", nullable = false)
    @NotNull(message = "Alergia é obrigatória")
    private Alergias alergia;

    // ========== DIAGNÓSTICO ==========

    @Embedded
    private DiagnosticoAlergiaPaciente diagnostico;

    // ========== HISTÓRICO DE REAÇÕES ==========

    @Embedded
    private HistoricoReacoesAlergiaPaciente historicoReacoes;

    // ========== OBSERVAÇÕES ==========

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes; // Observações específicas sobre a alergia neste paciente

    @Column(name = "alerta_medico", nullable = false)
    private Boolean alertaMedico = true; // Se deve aparecer alerta médico no prontuário

    @PrePersist
    @PreUpdate
    public void validateEmbeddables() {
        if (diagnostico == null) {
            diagnostico = new DiagnosticoAlergiaPaciente();
        }
        if (historicoReacoes == null) {
            historicoReacoes = new HistoricoReacoesAlergiaPaciente();
        }
    }
}
