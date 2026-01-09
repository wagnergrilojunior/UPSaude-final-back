package com.upsaude.entity.estabelecimento;
import com.upsaude.entity.BaseEntity;

import com.upsaude.entity.estabelecimento.Estabelecimentos;

import com.upsaude.entity.profissional.Medicos;

import com.upsaude.enums.TipoVinculoProfissionalEnum;
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

import java.time.OffsetDateTime;

@Entity
@Table(name = "medicos_estabelecimentos", schema = "public",
       uniqueConstraints = {
           @UniqueConstraint(name = "uk_medico_estabelecimento", columnNames = {"medico_id", "estabelecimento_id"})
       },
       indexes = {
           @Index(name = "idx_medico_estabelecimento_medico", columnList = "medico_id"),
           @Index(name = "idx_medico_estabelecimento_estabelecimento", columnList = "estabelecimento_id"),
           @Index(name = "idx_medico_estabelecimento_tipo_vinculo", columnList = "tipo_vinculo"),
           @Index(name = "idx_medico_estabelecimento_data_inicio", columnList = "data_inicio")
       })
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class MedicoEstabelecimento extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id", nullable = false)
    private Medicos medico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estabelecimento_id", nullable = false)
    private Estabelecimentos estabelecimento;

    @Column(name = "data_inicio", nullable = false)
    private OffsetDateTime dataInicio;

    @Column(name = "data_fim")
    private OffsetDateTime dataFim;

    @Convert(converter = TipoVinculoProfissionalEnumConverter.class)
    @Column(name = "tipo_vinculo", nullable = false)
    private TipoVinculoProfissionalEnum tipoVinculo;

    @Column(name = "carga_horaria_semanal")
    private Integer cargaHorariaSemanal;
}
