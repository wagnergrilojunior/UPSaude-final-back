package com.upsaude.entity;

import com.upsaude.enums.StatusReceitaEnum;
import com.upsaude.util.converter.StatusReceitaEnumConverter;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "receitas_medicas", schema = "public")
@Data
public class ReceitasMedicas extends BaseEntity {

    /**
     * Construtor padrão que inicializa as coleções para evitar NullPointerException.
     */
    public ReceitasMedicas() {
        this.medicacoes = new ArrayList<>();
    }

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

    @Convert(converter = StatusReceitaEnumConverter.class)
    @Column(name = "status", nullable = false)
    @NotNull(message = "Status é obrigatório")
    private StatusReceitaEnum status;

    @Column(name = "origem_receita", length = 50)
    @Size(max = 50, message = "Origem da receita deve ter no máximo 50 caracteres")
    private String origemReceita;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cid_principal_id")
    private CidDoencas cidPrincipal;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "receitas_medicacoes",
        schema = "public",
        joinColumns = @JoinColumn(name = "receita_id"),
        inverseJoinColumns = @JoinColumn(name = "medicacao_id")
    )
    private List<Medicacao> medicacoes = new ArrayList<>();

    // ========== MÉTODOS DE CICLO DE VIDA ==========

    /**
     * Garante que as coleções não sejam nulas antes de persistir ou atualizar.
     * Recria a lista se estiver nula.
     */
    @PrePersist
    @PreUpdate
    public void validateCollections() {
        if (medicacoes == null) {
            medicacoes = new ArrayList<>();
        }
    }
}
