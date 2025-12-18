package com.upsaude.entity.profissional;
import com.upsaude.entity.BaseEntity;

import com.upsaude.entity.profissional.ProfissionaisSaude;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profissional_id", nullable = false)
    @NotNull(message = "Profissional é obrigatório")
    private ProfissionaisSaude profissional;

    @Column(name = "tipo_evento", nullable = false, length = 50)
    @NotNull(message = "Tipo de evento é obrigatório")
    @Size(max = 50, message = "Tipo de evento deve ter no máximo 50 caracteres")
    private String tipoEvento;

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

    @Column(name = "numero_processo", length = 50)
    @Size(max = 50, message = "Número do processo deve ter no máximo 50 caracteres")
    private String numeroProcesso;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    @Column(name = "documento_referencia", length = 255)
    @Size(max = 255, message = "Documento de referência deve ter no máximo 255 caracteres")
    private String documentoReferencia;

    @Column(name = "usuario_responsavel")
    private java.util.UUID usuarioResponsavel;
}
