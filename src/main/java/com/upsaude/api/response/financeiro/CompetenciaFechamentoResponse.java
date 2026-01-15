package com.upsaude.api.response.financeiro;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompetenciaFechamentoResponse {

    private UUID competenciaId;
    private UUID tenantId;
    private String status;
    private OffsetDateTime fechadaEm;
    private UUID fechadaPor;
    private String motivoFechamento;
    private UUID documentoBpaId;
    private String hashMovimentacoes;
    private String hashBpa;
    private String snapshotHash;
    private Boolean validacaoIntegridade;
    private String mensagem;
}
