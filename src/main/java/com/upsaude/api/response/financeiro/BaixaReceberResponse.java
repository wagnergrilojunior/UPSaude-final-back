package com.upsaude.api.response.financeiro;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaixaReceberResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;

    private TituloReceberSimplificadoResponse tituloReceber;
    private ContaFinanceiraSimplificadaResponse contaFinanceira;
    private MovimentacaoContaSimplificadaResponse movimentacaoConta;
    private LancamentoFinanceiroSimplificadoResponse lancamentoFinanceiro;

    private BigDecimal valorPago;
    private LocalDate dataPagamento;
    private String meioPagamento;
    private String status;
    private String observacao;
}

