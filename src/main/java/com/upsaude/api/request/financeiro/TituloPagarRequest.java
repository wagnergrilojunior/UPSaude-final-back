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
@Schema(description = "Dados de título a pagar")
public class TituloPagarRequest {

    @NotNull(message = "Fornecedor é obrigatório")
    private UUID fornecedor;

    @NotNull(message = "Conta contábil de despesa é obrigatória")
    private UUID contaContabilDespesa;

    @NotNull(message = "Centro de custo é obrigatório")
    private UUID centroCusto;

    private UUID recorrenciaFinanceira;

    @NotBlank(message = "Número do documento é obrigatório")
    @Size(max = 100, message = "Número do documento deve ter no máximo 100 caracteres")
    private String numeroDocumento;

    @NotNull(message = "Valor original é obrigatório")
    @Positive(message = "Valor original deve ser maior que zero")
    private BigDecimal valorOriginal;

    @NotNull(message = "Valor em aberto é obrigatório")
    private BigDecimal valorAberto;

    @NotNull(message = "Data de emissão é obrigatória")
    private LocalDate dataEmissao;

    @NotNull(message = "Data de vencimento é obrigatória")
    private LocalDate dataVencimento;

    @NotBlank(message = "Status é obrigatório")
    @Size(max = 30, message = "Status deve ter no máximo 30 caracteres")
private String status;
}

