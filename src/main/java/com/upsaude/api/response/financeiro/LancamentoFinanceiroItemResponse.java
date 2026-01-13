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
public class LancamentoFinanceiroItemResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;

    private LancamentoFinanceiroSimplificadoResponse lancamento;
    private ContaContabilSimplificadaResponse contaContabil;
    private CentroCustoSimplificadoResponse centroCusto;

    private String tipoPartida;
    private BigDecimal valor;
    private String historico;
}

