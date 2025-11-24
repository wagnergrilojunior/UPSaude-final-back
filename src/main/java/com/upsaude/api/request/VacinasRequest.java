package com.upsaude.api.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VacinasRequest {
    private String nome;
    private String descricao;
    private String fabricante;
    private String lote;
    private LocalDate dataValidade;
    private BigDecimal doseMl;
}

