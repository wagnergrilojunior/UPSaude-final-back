package com.upsaude.api.response.faturamento;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentoFaturamentoItemSimplificadoResponse {
    private UUID id;
    private Integer quantidade;
    private BigDecimal valorTotal;
}

