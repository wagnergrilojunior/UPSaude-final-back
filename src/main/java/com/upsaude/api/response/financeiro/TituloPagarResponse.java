package com.upsaude.api.response.financeiro;

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
public class TituloPagarResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;

    private ParteFinanceiraSimplificadaResponse fornecedor;
    private ContaContabilSimplificadaResponse contaContabilDespesa;
    private CentroCustoSimplificadoResponse centroCusto;
    private RecorrenciaFinanceiraSimplificadaResponse recorrenciaFinanceira;

    private String numeroDocumento;
    private BigDecimal valorOriginal;
    private BigDecimal valorAberto;
    private LocalDate dataEmissao;
    private LocalDate dataVencimento;
    private String status;

    @Builder.Default
    private List<PagamentoPagarSimplificadaResponse> pagamentos = new ArrayList<>();
}

