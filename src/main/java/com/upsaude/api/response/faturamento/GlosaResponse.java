package com.upsaude.api.response.faturamento;

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
public class GlosaResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;

    private DocumentoFaturamentoSimplificadoResponse documento;
    private DocumentoFaturamentoItemSimplificadoResponse item;
    private String tipo;
    private BigDecimal valorGlosado;
    private String motivoCodigo;
    private String motivoDescricao;
    private String status;
}

