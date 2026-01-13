package com.upsaude.api.response.financeiro;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConciliacaoBancariaSimplificadaResponse {
    private UUID id;
    private LocalDate periodoInicio;
    private LocalDate periodoFim;
    private String status;
}

