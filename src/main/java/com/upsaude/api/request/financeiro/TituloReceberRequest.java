package com.upsaude.api.request.financeiro;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
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
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Dados de título a receber")
public class TituloReceberRequest {

    private UUID documentoFaturamento;

    @NotNull(message = "Pagador é obrigatório")
    private UUID pagador;

    @NotNull(message = "Conta contábil de receita é obrigatória")
    private UUID contaContabilReceita;

    private UUID centroCusto;

    @NotBlank(message = "Número do título é obrigatório")
    @Size(max = 100, message = "Número deve ter no máximo 100 caracteres")
    private String numero;

    private Integer parcela;
    private Integer totalParcelas;

    @NotNull(message = "Valor original é obrigatório")
    @Positive(message = "Valor original deve ser maior que zero")
    private BigDecimal valorOriginal;

    private BigDecimal desconto;
    private BigDecimal juros;
    private BigDecimal multa;

    @NotNull(message = "Valor em aberto é obrigatório")
    private BigDecimal valorAberto;

    @NotNull(message = "Data de emissão é obrigatória")
    private LocalDate dataEmissao;

    @NotNull(message = "Data de vencimento é obrigatória")
    private LocalDate dataVencimento;

    @NotBlank(message = "Status é obrigatório")
    @Size(max = 30, message = "Status deve ter no máximo 30 caracteres")
    private String status; // ABERTO | PARCIAL | PAGO | CANCELADO_POR_REVERSAO | RENEGOCIADO
}

