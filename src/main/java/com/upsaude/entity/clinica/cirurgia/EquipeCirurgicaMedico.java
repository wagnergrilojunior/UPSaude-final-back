package com.upsaude.entity.clinica.cirurgia;

import com.upsaude.entity.BaseEntity;
import com.upsaude.entity.profissional.Medicos;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "equipe_cirurgica_medico", schema = "public",
       uniqueConstraints = {
           @UniqueConstraint(name = "uk_equipe_cirurgica_medico", columnNames = {"equipe_cirurgica_id", "medico_id"})
       },
       indexes = {
           @Index(name = "idx_equipe_cirurgica_medico_equipe", columnList = "equipe_cirurgica_id"),
           @Index(name = "idx_equipe_cirurgica_medico_medico", columnList = "medico_id")
       })
@Data
@EqualsAndHashCode(callSuper = true)
public class EquipeCirurgicaMedico extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipe_cirurgica_id", nullable = false)
    private EquipeCirurgica equipeCirurgica;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id", nullable = false)
    private Medicos medico;

    @Column(name = "funcao", length = 100)
    private String funcao;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;
}

