package com.upsaude.api.response.financeiro;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
public class ConciliacaoBancariaResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;

    private ContaFinanceiraSimplificadaResponse contaFinanceira;
    private LocalDate periodoInicio;
    private LocalDate periodoFim;
    private String status;
    private OffsetDateTime fechadaEm;

    @Builder.Default
    private List<ConciliacaoItemResponse> itens = new ArrayList<>();
}

