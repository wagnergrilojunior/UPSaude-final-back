package com.upsaude.entity;

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
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
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
@Data
@EqualsAndHashCode(callSuper = true)
public class MedicoEstabelecimento extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id", nullable = false)
    @NotNull(message = "Médico é obrigatório")
    private Medicos medico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estabelecimento_id", nullable = false)
    @NotNull(message = "Estabelecimento é obrigatório")
    private Estabelecimentos estabelecimento;

    @Column(name = "data_inicio", nullable = false)
    @NotNull(message = "Data de início do vínculo é obrigatória")
    private OffsetDateTime dataInicio;

    @Column(name = "data_fim")
    private OffsetDateTime dataFim;

    @Convert(converter = TipoVinculoProfissionalEnumConverter.class)
    @Column(name = "tipo_vinculo", nullable = false)
    @NotNull(message = "Tipo de vínculo é obrigatório")
    private TipoVinculoProfissionalEnum tipoVinculo;

    @Column(name = "carga_horaria_semanal")
    private Integer cargaHorariaSemanal;

    @Column(name = "salario", precision = 10, scale = 2)
    private BigDecimal salario;

    @Column(name = "numero_matricula", length = 50)
    @Size(max = 50, message = "Número de matrícula deve ter no máximo 50 caracteres")
    private String numeroMatricula;

    @Column(name = "setor_departamento", length = 255)
    @Size(max = 255, message = "Setor/Departamento deve ter no máximo 255 caracteres")
    private String setorDepartamento;

    @Column(name = "cargo_funcao", length = 255)
    @Size(max = 255, message = "Cargo/Função deve ter no máximo 255 caracteres")
    private String cargoFuncao;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;
}
