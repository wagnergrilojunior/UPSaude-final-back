package com.upsaude.entity.financeiro;

import com.upsaude.entity.BaseEntity;
import com.upsaude.entity.agendamento.Agendamento;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(
    name = "reserva_orcamentaria_assistencial",
    schema = "public",
    indexes = {
        @Index(name = "idx_reserva_orcamentaria_competencia", columnList = "tenant_id, competencia_id, status"),
        @Index(name = "idx_reserva_orcamentaria_agendamento", columnList = "agendamento_id"),
        @Index(name = "idx_reserva_orcamentaria_prestador", columnList = "tenant_id, prestador_tipo, prestador_id")
    }
)
@Data
@EqualsAndHashCode(callSuper = true)
public class ReservaOrcamentariaAssistencial extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "competencia_id", nullable = false)
    private CompetenciaFinanceira competencia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agendamento_id")
    private Agendamento agendamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "documento_faturamento_id")
    private com.upsaude.entity.faturamento.DocumentoFaturamento documentoFaturamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guia_ambulatorial_id")
    private GuiaAtendimentoAmbulatorial guiaAmbulatorial;

    @Column(name = "prestador_id")
    private UUID prestadorId; // Pode ser estabelecimento ou profissional

    @Column(name = "prestador_tipo", length = 30)
    private String prestadorTipo; // ESTABELECIMENTO | PROFISSIONAL

    @Column(name = "valor_reservado_total", nullable = false, precision = 14, scale = 2)
    private BigDecimal valorReservadoTotal;

    @Column(name = "status", nullable = false, length = 20)
    private String status; // ATIVA | CONSUMIDA | LIBERADA | PARCIAL

    @Column(name = "grupo_reserva")
    private UUID grupoReserva;

    @Column(name = "idempotency_key", nullable = false, length = 255)
    private String idempotencyKey;
}
