package com.upsaude.api.response.financeiro;

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
public class OrcamentoCompetenciaResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;

    private CompetenciaFinanceiraResponse competencia;
    private BigDecimal saldoAnterior;
    private BigDecimal creditos;
    private BigDecimal reservasAtivas;
    private BigDecimal consumos;
    private BigDecimal estornos;
    private BigDecimal despesasAdmin;
    private BigDecimal saldoFinal;
    private OffsetDateTime atualizadoEm;

    // Campo calculado (helper de domínio)
    private BigDecimal saldoDisponivel;
}

