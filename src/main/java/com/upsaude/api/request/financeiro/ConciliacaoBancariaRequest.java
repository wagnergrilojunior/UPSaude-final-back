package com.upsaude.api.request.financeiro;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Dados de conciliação bancária")
public class ConciliacaoBancariaRequest {

    @NotNull(message = "Conta financeira é obrigatória")
    private UUID contaFinanceira;

    @NotNull(message = "Período início é obrigatório")
    private LocalDate periodoInicio;

    @NotNull(message = "Período fim é obrigatório")
    private LocalDate periodoFim;

    @NotBlank(message = "Status é obrigatório")
    @Size(max = 20, message = "Status deve ter no máximo 20 caracteres")
    private String status; // ABERTA | FECHADA
}

