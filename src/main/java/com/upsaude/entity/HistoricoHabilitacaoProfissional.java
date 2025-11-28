package com.upsaude.entity;

import com.upsaude.enums.StatusAtivoEnum;
import com.upsaude.util.converter.StatusAtivoEnumConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.OffsetDateTime;

/**
 * Entidade que representa o histórico de habilitação profissional.
 * Registra eventos de renovação, suspensão, revalidação e alterações de status do registro profissional.
 * Permite rastreabilidade completa para auditoria e compliance.
 *
 * @author UPSaúde
 */
@Entity
@Table(name = "historico_habilitacao_profissional", schema = "public",
       indexes = {
           @Index(name = "idx_historico_profissional", columnList = "profissional_id"),
           @Index(name = "idx_historico_data_evento", columnList = "data_evento"),
           @Index(name = "idx_historico_tipo_evento", columnList = "tipo_evento")
       })
@Data
@EqualsAndHashCode(callSuper = true)
public class HistoricoHabilitacaoProfissional extends BaseEntity {

    // ========== RELACIONAMENTO COM PROFISSIONAL ==========

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profissional_id", nullable = false)
    @NotNull(message = "Profissional é obrigatório")
    private ProfissionaisSaude profissional;

    // ========== DADOS DO EVENTO ==========

    @Column(name = "tipo_evento", nullable = false, length = 50)
    @NotNull(message = "Tipo de evento é obrigatório")
    @Size(max = 50, message = "Tipo de evento deve ter no máximo 50 caracteres")
    private String tipoEvento; // Ex: RENOVACAO, SUSPENSAO, REVALIDACAO, ALTERACAO_STATUS, EMISSAO

    @Column(name = "data_evento", nullable = false)
    @NotNull(message = "Data do evento é obrigatória")
    private OffsetDateTime dataEvento;

    @Convert(converter = StatusAtivoEnumConverter.class)
    @Column(name = "status_anterior")
    private StatusAtivoEnum statusAnterior;

    @Convert(converter = StatusAtivoEnumConverter.class)
    @Column(name = "status_novo")
    private StatusAtivoEnum statusNovo;

    @Column(name = "data_validade_anterior")
    private OffsetDateTime dataValidadeAnterior;

    @Column(name = "data_validade_nova")
    private OffsetDateTime dataValidadeNova;

    // ========== INFORMAÇÕES COMPLEMENTARES ==========

    @Column(name = "numero_processo", length = 50)
    @Size(max = 50, message = "Número do processo deve ter no máximo 50 caracteres")
    private String numeroProcesso; // Número do processo no conselho, se aplicável

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes; // Observações sobre o evento

    @Column(name = "documento_referencia", length = 255)
    @Size(max = 255, message = "Documento de referência deve ter no máximo 255 caracteres")
    private String documentoReferencia; // Referência a documento que originou o evento (ex: número de portaria, resolução)

    @Column(name = "usuario_responsavel")
    private java.util.UUID usuarioResponsavel; // ID do usuário que registrou o evento
}

