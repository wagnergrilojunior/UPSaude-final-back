package com.upsaude.entity.clinica.cirurgia;
import com.upsaude.entity.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "equipe_cirurgica", schema = "public",
       indexes = {
           @Index(name = "idx_equipe_cirurgica_cirurgia", columnList = "cirurgia_id")
       })
@Data
@EqualsAndHashCode(callSuper = true)
public class EquipeCirurgica extends BaseEntity {

    public EquipeCirurgica() {
        this.medicos = new ArrayList<>();
        this.profissionais = new ArrayList<>();
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cirurgia_id", nullable = false)
    private Cirurgia cirurgia;

    @OneToMany(mappedBy = "equipeCirurgica", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EquipeCirurgicaProfissional> profissionais = new ArrayList<>();

    @OneToMany(mappedBy = "equipeCirurgica", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EquipeCirurgicaMedico> medicos = new ArrayList<>();

    @Column(name = "eh_principal")
    private Boolean ehPrincipal;

    @Column(name = "valor_participacao", precision = 10, scale = 2)
    private BigDecimal valorParticipacao;

    @Column(name = "percentual_participacao", precision = 5, scale = 2)
    private BigDecimal percentualParticipacao;

    @Column(name = "data_hora_entrada")
    private java.time.OffsetDateTime dataHoraEntrada;

    @Column(name = "data_hora_saida")
    private java.time.OffsetDateTime dataHoraSaida;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    @PrePersist
    @PreUpdate
    public void validateCollections() {
        if (profissionais == null) {
            profissionais = new ArrayList<>();
        }
        if (medicos == null) {
            medicos = new ArrayList<>();
        }
    }

    public void addProfissional(EquipeCirurgicaProfissional profissional) {
        if (profissional == null) {
            return;
        }
        if (profissionais == null) {
            profissionais = new ArrayList<>();
        }
        if (!profissionais.contains(profissional)) {
            profissionais.add(profissional);
            profissional.setEquipeCirurgica(this);
        }
    }

    public void removeProfissional(EquipeCirurgicaProfissional profissional) {
        if (profissional == null || profissionais == null) {
            return;
        }
        if (profissionais.remove(profissional)) {
            profissional.setEquipeCirurgica(null);
        }
    }

    public void addMedico(EquipeCirurgicaMedico medico) {
        if (medico == null) {
            return;
        }
        if (medicos == null) {
            medicos = new ArrayList<>();
        }
        if (!medicos.contains(medico)) {
            medicos.add(medico);
            medico.setEquipeCirurgica(this);
        }
    }

    public void removeMedico(EquipeCirurgicaMedico medico) {
        if (medico == null || medicos == null) {
            return;
        }
        if (medicos.remove(medico)) {
            medico.setEquipeCirurgica(null);
        }
    }
}
