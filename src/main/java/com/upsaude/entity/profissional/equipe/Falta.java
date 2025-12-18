package com.upsaude.entity.profissional.equipe;
import com.upsaude.entity.profissional.Medicos;
import com.upsaude.entity.BaseEntity;

import com.upsaude.entity.profissional.ProfissionaisSaude;

import com.upsaude.enums.TipoFaltaEnum;
import com.upsaude.util.converter.TipoFaltaEnumConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Entity
@Table(name = "faltas", schema = "public",
       indexes = {
           @Index(name = "idx_falta_profissional", columnList = "profissional_id"),
           @Index(name = "idx_falta_data", columnList = "data_falta"),
           @Index(name = "idx_falta_tipo", columnList = "tipo_falta"),
           @Index(name = "idx_falta_estabelecimento", columnList = "estabelecimento_id"),
           @Index(name = "idx_falta_periodo", columnList = "data_inicio,data_fim")
       })
@Data
@EqualsAndHashCode(callSuper = true)
public class Falta extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profissional_id", nullable = false)
    @NotNull(message = "Profissional de saúde é obrigatório")
    private ProfissionaisSaude profissional;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id")
    private Medicos medico;

    @Column(name = "data_falta", nullable = false)
    @NotNull(message = "Data da falta é obrigatória")
    private LocalDate dataFalta;

    @Column(name = "data_inicio")
    private LocalDate dataInicio;

    @Column(name = "data_fim")
    private LocalDate dataFim;

    @Convert(converter = TipoFaltaEnumConverter.class)
    @Column(name = "tipo_falta", nullable = false)
    @NotNull(message = "Tipo de falta é obrigatório")
    private TipoFaltaEnum tipoFalta;

    @Column(name = "justificativa", columnDefinition = "TEXT")
    private String justificativa;

    @Column(name = "documento_comprobatorio", length = 500)
    private String documentoComprobatorio;

    @Column(name = "numero_atestado", length = 100)
    private String numeroAtestado;

    @Column(name = "aprovado")
    private Boolean aprovado;

    @Column(name = "aprovado_por")
    private java.util.UUID aprovadoPor;

    @Column(name = "data_aprovacao")
    private java.time.OffsetDateTime dataAprovacao;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;
}
