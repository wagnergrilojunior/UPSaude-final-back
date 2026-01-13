package com.upsaude.api.request.financeiro;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
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
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Dados de orçamento por competência")
public class OrcamentoCompetenciaRequest {

    @NotNull(message = "Competência financeira é obrigatória")
    private UUID competencia;

    private BigDecimal saldoAnterior;
    private BigDecimal creditos;
    private BigDecimal reservasAtivas;
    private BigDecimal consumos;
    private BigDecimal estornos;
    private BigDecimal despesasAdmin;
    private BigDecimal saldoFinal;
}

