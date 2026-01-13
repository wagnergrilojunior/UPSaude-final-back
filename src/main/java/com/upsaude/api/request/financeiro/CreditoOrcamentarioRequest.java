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
@Schema(description = "Dados de crédito orçamentário")
public class CreditoOrcamentarioRequest {

    @NotNull(message = "Competência financeira é obrigatória")
    private UUID competencia;

    @NotNull(message = "Valor é obrigatório")
    @Positive(message = "Valor deve ser maior que zero")
    private BigDecimal valor;

    @NotBlank(message = "Fonte é obrigatória")
    @Size(max = 50, message = "Fonte deve ter no máximo 50 caracteres")
    private String fonte;

    @Size(max = 255, message = "Documento de referência deve ter no máximo 255 caracteres")
    private String documentoReferencia;

    @NotNull(message = "Data do crédito é obrigatória")
    private LocalDate dataCredito;
}

