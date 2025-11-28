package com.upsaude.entity;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "receitas_medicas", schema = "public")
@Data
public class ReceitasMedicas extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id", nullable = false)
    @NotNull(message = "Médico é obrigatório")
    private Medicos medico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    @NotNull(message = "Paciente é obrigatório")
    private Paciente paciente;

    @Column(name = "numero_receita", nullable = false, length = 50)
    @NotBlank(message = "Número da receita é obrigatório")
    @Size(max = 50, message = "Número da receita deve ter no máximo 50 caracteres")
    private String numeroReceita;

    @Column(name = "data_prescricao", nullable = false)
    @NotNull(message = "Data de prescrição é obrigatória")
    private OffsetDateTime dataPrescricao;

    @Column(name = "data_validade", nullable = false)
    @NotNull(message = "Data de validade é obrigatória")
    private OffsetDateTime dataValidade;

    @Column(name = "uso_continuo", nullable = false)
    @NotNull(message = "Indicação de uso contínuo é obrigatória")
    private Boolean usoContinuo;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    @Size(max = 1000, message = "Observações devem ter no máximo 1000 caracteres")
    private String observacoes;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status", nullable = false)
    @NotNull(message = "Status é obrigatório")
    private StatusReceita status;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "receitas_medicamentos",
        schema = "public",
        joinColumns = @JoinColumn(name = "receita_id"),
        inverseJoinColumns = @JoinColumn(name = "medicamento_id")
    )
    private List<Medicamentos> medicamentos = new ArrayList<>();

    public enum StatusReceita {
        ATIVA("Ativa"),
        UTILIZADA("Utilizada"),
        CANCELADA("Cancelada"),
        VENCIDA("Vencida");

        private final String descricao;

        StatusReceita(String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }
    }
}
