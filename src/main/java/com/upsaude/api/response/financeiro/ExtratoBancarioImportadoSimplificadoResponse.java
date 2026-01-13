package com.upsaude.api.response.financeiro;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExtratoBancarioImportadoSimplificadoResponse {
    private UUID id;
    private LocalDate data;
    private BigDecimal valor;
    private String descricao;
    private String documento;
    private String statusConciliacao;
}

