package com.upsaude.entity.profissional.equipe;
import com.upsaude.entity.BaseEntity;

import com.upsaude.entity.estabelecimento.Estabelecimentos;

import com.upsaude.enums.StatusAtivoEnum;
import com.upsaude.enums.TipoEquipeEnum;
import com.upsaude.util.converter.StatusAtivoEnumConverter;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "equipes_saude", schema = "public",
       uniqueConstraints = {
           @UniqueConstraint(name = "uk_equipes_saude_ine_estabelecimento", columnNames = {"ine", "estabelecimento_id"})
       },
       indexes = {
           @Index(name = "idx_equipes_saude_ine", columnList = "ine"),
           @Index(name = "idx_equipes_saude_estabelecimento", columnList = "estabelecimento_id"),
           @Index(name = "idx_equipes_saude_status", columnList = "status")
       })
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class EquipeSaude extends BaseEntity {

    @Column(name = "ine", nullable = false, length = 15)
    private String ine;

    @Column(name = "nome_referencia", nullable = false, length = 255)
    private String nomeReferencia;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_equipe", nullable = false, length = 100)
    private TipoEquipeEnum tipoEquipe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estabelecimento_id", nullable = false)
    private Estabelecimentos estabelecimento;

    @Column(name = "data_ativacao", nullable = false)
    private OffsetDateTime dataAtivacao;

    @Column(name = "data_inativacao")
    private OffsetDateTime dataInativacao;

    @Convert(converter = StatusAtivoEnumConverter.class)
    @Column(name = "status", nullable = false)
    private StatusAtivoEnum status;

    @OneToMany(mappedBy = "equipe", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<VinculoProfissionalEquipe> vinculosProfissionais = new HashSet<>();

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    public EquipeSaude() {
        this.vinculosProfissionais = new HashSet<>();
    }

    @PrePersist
    @PreUpdate
    public void validateCollections() {
        if (vinculosProfissionais == null) {
            vinculosProfissionais = new HashSet<>();
        }
    }

    public void addVinculoProfissional(VinculoProfissionalEquipe vinculo) {
        if (vinculo == null) {
            return;
        }
        if (vinculosProfissionais == null) {
            vinculosProfissionais = new HashSet<>();
        }
        if (!vinculosProfissionais.contains(vinculo)) {
            vinculosProfissionais.add(vinculo);
            vinculo.setEquipe(this);
        }
    }

    public void removeVinculoProfissional(VinculoProfissionalEquipe vinculo) {
        if (vinculo == null || vinculosProfissionais == null) {
            return;
        }
        if (vinculosProfissionais.remove(vinculo)) {
            vinculo.setEquipe(null);
        }
    }
}
