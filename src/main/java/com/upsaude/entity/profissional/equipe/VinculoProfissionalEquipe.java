package com.upsaude.entity.profissional.equipe;
import java.time.OffsetDateTime;

import com.upsaude.entity.BaseEntity;
import com.upsaude.entity.profissional.ProfissionaisSaude;
import com.upsaude.enums.StatusAtivoEnum;
import com.upsaude.enums.TipoVinculoProfissionalEnum;
import com.upsaude.util.converter.StatusAtivoEnumConverter;
import com.upsaude.util.converter.TipoVinculoProfissionalEnumConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "vinculos_profissional_equipe", schema = "public",
       uniqueConstraints = {
           @UniqueConstraint(name = "uk_profissional_equipe", columnNames = {"profissional_id", "equipe_id"})
       },
       indexes = {
           @Index(name = "idx_vinculo_profissional_equipe_profissional", columnList = "profissional_id"),
           @Index(name = "idx_vinculo_profissional_equipe_equipe", columnList = "equipe_id"),
           @Index(name = "idx_vinculo_profissional_equipe_tipo_vinculo", columnList = "tipo_vinculo"),
           @Index(name = "idx_vinculo_profissional_equipe_status", columnList = "status"),
           @Index(name = "idx_vinculo_profissional_equipe_data_inicio", columnList = "data_inicio")
       })
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class VinculoProfissionalEquipe extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profissional_id", nullable = false)
    private ProfissionaisSaude profissional;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipe_id", nullable = false)
    private EquipeSaude equipe;

    @Column(name = "data_inicio", nullable = false)
    private OffsetDateTime dataInicio;

    @Column(name = "data_fim")
    private OffsetDateTime dataFim;

    @Convert(converter = TipoVinculoProfissionalEnumConverter.class)
    @Column(name = "tipo_vinculo", nullable = false)
    private TipoVinculoProfissionalEnum tipoVinculo;

    @Column(name = "funcao_equipe", length = 255)
    private String funcaoEquipe;

    @Column(name = "carga_horaria_semanal")
    private Integer cargaHorariaSemanal;

    @Convert(converter = StatusAtivoEnumConverter.class)
    @Column(name = "status", nullable = false)
    private StatusAtivoEnum status;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;
}
