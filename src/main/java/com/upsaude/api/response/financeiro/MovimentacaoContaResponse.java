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
public class MovimentacaoContaResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;

    private ContaFinanceiraSimplificadaResponse contaFinanceira;
    private BaixaReceberSimplificadaResponse baixaReceber;
    private PagamentoPagarSimplificadaResponse pagamentoPagar;
    private TransferenciaEntreContasSimplificadaResponse transferencia;
    private LancamentoFinanceiroSimplificadoResponse lancamentoFinanceiro;

    private String tipo;
    private BigDecimal valor;
    private OffsetDateTime dataMovimento;
    private String status;
}

