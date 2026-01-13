package com.upsaude.api.response.faturamento;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentoFaturamentoSimplificadoResponse {
    private UUID id;
    private String tipo;
    private String numero;
    private String serie;
    private String status;
}

