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
public class TituloReceberSimplificadoResponse {
    private UUID id;
    private String numero;
    private BigDecimal valorAberto;
    private LocalDate dataVencimento;
    private String status;
}

