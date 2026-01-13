package com.upsaude.api.response.financeiro;

import com.upsaude.api.response.agendamento.AgendamentoSimplificadoResponse;
import com.upsaude.api.response.faturamento.DocumentoFaturamentoSimplificadoResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservaOrcamentariaAssistencialResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;

    private CompetenciaFinanceiraResponse competencia;
    private AgendamentoSimplificadoResponse agendamento;
    private GuiaAtendimentoAmbulatorialSimplificadoResponse guiaAmbulatorial;
    private DocumentoFaturamentoSimplificadoResponse documentoFaturamento;

    private UUID prestadorId;
    private String prestadorTipo;

    private BigDecimal valorReservadoTotal;
    private String status;
    private UUID grupoReserva;
}

