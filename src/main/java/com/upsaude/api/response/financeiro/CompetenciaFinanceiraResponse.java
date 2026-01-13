package com.upsaude.api.response.financeiro;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompetenciaFinanceiraResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private String codigo;
    private String tipo; // MENSAL | CUSTOM
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private String descricao;
}

