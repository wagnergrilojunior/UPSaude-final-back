package com.upsaude.entity;

import com.upsaude.enums.TipoPontoEnum;
import com.upsaude.util.converter.TipoPontoEnumConverter;
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

import java.time.OffsetDateTime;

/**
 * Entidade que representa o controle de ponto de profissionais de saúde.
 * Armazena registros de entrada, saída e intervalos para controle de jornada de trabalho.
 *
 * @author UPSaúde
 */
@Entity
@Table(name = "controle_ponto", schema = "public",
       indexes = {
           @Index(name = "idx_controle_ponto_profissional", columnList = "profissional_id"),
           @Index(name = "idx_controle_ponto_data_hora", columnList = "data_hora"),
           @Index(name = "idx_controle_ponto_tipo", columnList = "tipo_ponto"),
           @Index(name = "idx_controle_ponto_estabelecimento", columnList = "estabelecimento_id"),
           @Index(name = "idx_controle_ponto_data", columnList = "data_ponto")
       })
@Data
@EqualsAndHashCode(callSuper = true)
public class ControlePonto extends BaseEntity {

    // ========== RELACIONAMENTOS ==========

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profissional_id", nullable = false)
    @NotNull(message = "Profissional de saúde é obrigatório")
    private ProfissionaisSaude profissional;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id")
    private Medicos medico; // Opcional: para médicos específicos

    // ========== DADOS DO PONTO ==========

    @Column(name = "data_hora", nullable = false)
    @NotNull(message = "Data e hora do ponto são obrigatórias")
    private OffsetDateTime dataHora;

    @Column(name = "data_ponto", nullable = false)
    @NotNull(message = "Data do ponto é obrigatória")
    private java.time.LocalDate dataPonto;

    @Convert(converter = TipoPontoEnumConverter.class)
    @Column(name = "tipo_ponto", nullable = false)
    @NotNull(message = "Tipo de ponto é obrigatório")
    private TipoPontoEnum tipoPonto;

    // ========== LOCALIZAÇÃO ==========

    @Column(name = "latitude")
    private Double latitude; // Para validação de geolocalização

    @Column(name = "longitude")
    private Double longitude; // Para validação de geolocalização

    @Column(name = "endereco_ip", length = 45)
    private String enderecoIp; // Para auditoria

    // ========== INFORMAÇÕES COMPLEMENTARES ==========

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    @Column(name = "justificativa", columnDefinition = "TEXT")
    private String justificativa; // Para pontos atípicos ou ajustes

    @Column(name = "aprovado")
    private Boolean aprovado; // Se o ponto foi aprovado pelo gestor

    @Column(name = "aprovado_por")
    private java.util.UUID aprovadoPor; // ID do usuário que aprovou

    @Column(name = "data_aprovacao")
    private OffsetDateTime dataAprovacao;
}

