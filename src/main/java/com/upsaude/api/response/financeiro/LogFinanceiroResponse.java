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
public class LogFinanceiroResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;

    private String entidadeTipo;
    private UUID entidadeId;
    private String acao;
    private UUID usuarioId;
    private String correlationId;
    private String payloadAntes;
    private String payloadDepois;
    private String ip;
    private String userAgent;
    private OffsetDateTime criadoEm;
}

