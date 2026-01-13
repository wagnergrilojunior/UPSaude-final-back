package com.upsaude.api.response.financeiro;

import com.upsaude.api.response.faturamento.DocumentoFaturamentoSimplificadoResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TituloReceberResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;

    private DocumentoFaturamentoSimplificadoResponse documentoFaturamento;
    private ParteFinanceiraSimplificadaResponse pagador;
    private ContaContabilSimplificadaResponse contaContabilReceita;
    private CentroCustoSimplificadoResponse centroCusto;

    private String numero;
    private Integer parcela;
    private Integer totalParcelas;
    private BigDecimal valorOriginal;
    private BigDecimal desconto;
    private BigDecimal juros;
    private BigDecimal multa;
    private BigDecimal valorAberto;
    private LocalDate dataEmissao;
    private LocalDate dataVencimento;
    private String status;

    @Builder.Default
    private List<BaixaReceberSimplificadaResponse> baixas = new ArrayList<>();
}

