package com.upsaude.api.response.financeiro;

import com.upsaude.api.response.faturamento.DocumentoFaturamentoSimplificadoResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompetenciaFinanceiraTenantResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;

    private CompetenciaFinanceiraResponse competencia;
    private String status;
    private OffsetDateTime fechadaEm;
    private UUID fechadaPor;
    private String motivoFechamento;
    private String snapshotHash;

    private DocumentoFaturamentoSimplificadoResponse documentoBpaFechamento;
    private String hashMovimentacoes;
    private String hashBpa;
    private Boolean validacaoIntegridade;
}

