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
public class ExtratoBancarioImportadoResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;

    private ContaFinanceiraSimplificadaResponse contaFinanceira;
    private String hashLinha;
    private String descricao;
    private BigDecimal valor;
    private LocalDate data;
    private String documento;
    private BigDecimal saldoApos;
    private String statusConciliacao;
}

