package com.upsaude.api.response.financeiro;

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
public class RecorrenciaFinanceiraSimplificadaResponse {
    private UUID id;
    private String tipo;
    private String periodicidade;
    private OffsetDateTime proximaGeracaoEm;
    private Boolean ativo;
}

