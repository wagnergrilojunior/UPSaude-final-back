package com.upsaude.entity;

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

/**
 * Entidade que representa o registro de faltas de profissionais de saúde.
 * Armazena informações sobre faltas, licenças e ausências dos profissionais.
 *
 * @author UPSaúde
 */
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

    // ========== RELACIONAMENTOS ==========

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profissional_id", nullable = false)
    @NotNull(message = "Profissional de saúde é obrigatório")
    private ProfissionaisSaude profissional;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id")
    private Medicos medico; // Opcional: para médicos específicos

    // ========== DADOS DA FALTA ==========

    @Column(name = "data_falta", nullable = false)
    @NotNull(message = "Data da falta é obrigatória")
    private LocalDate dataFalta;

    @Column(name = "data_inicio")
    private LocalDate dataInicio; // Para faltas/ausências em período

    @Column(name = "data_fim")
    private LocalDate dataFim; // Para faltas/ausências em período

    @Convert(converter = TipoFaltaEnumConverter.class)
    @Column(name = "tipo_falta", nullable = false)
    @NotNull(message = "Tipo de falta é obrigatório")
    private TipoFaltaEnum tipoFalta;

    // ========== JUSTIFICATIVA E DOCUMENTAÇÃO ==========

    @Column(name = "justificativa", columnDefinition = "TEXT")
    private String justificativa;

    @Column(name = "documento_comprobatorio", length = 500)
    private String documentoComprobatorio; // URL ou caminho do documento

    @Column(name = "numero_atestado", length = 100)
    private String numeroAtestado; // Número do atestado médico

    // ========== APROVAÇÃO ==========

    @Column(name = "aprovado")
    private Boolean aprovado;

    @Column(name = "aprovado_por")
    private java.util.UUID aprovadoPor; // ID do usuário que aprovou

    @Column(name = "data_aprovacao")
    private java.time.OffsetDateTime dataAprovacao;

    // ========== OBSERVAÇÕES ==========

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;
}

