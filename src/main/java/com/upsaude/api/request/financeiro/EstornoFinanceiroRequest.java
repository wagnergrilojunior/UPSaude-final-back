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
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Dados de estorno financeiro")
public class EstornoFinanceiroRequest {

    @NotNull(message = "Competência financeira é obrigatória")
    private UUID competencia;

    private UUID agendamento;
    private UUID atendimento;
    private UUID guiaAmbulatorial;

    @NotNull(message = "Paciente é obrigatório")
    private UUID paciente;

    private UUID atendimentoProcedimento;

    @NotBlank(message = "Motivo é obrigatório")
    @Size(max = 30, message = "Motivo deve ter no máximo 30 caracteres")
    private String motivo; // CANCELAMENTO | FALTA_PACIENTE | NAO_EXECUTADO | AJUSTE | OUTRO

    @NotNull(message = "Valor estornado é obrigatório")
    @Positive(message = "Valor estornado deve ser maior que zero")
    private BigDecimal valorEstornado;

    private String procedimentoCodigo;
    private String procedimentoNome;

    @NotNull(message = "Data do estorno é obrigatória")
    private OffsetDateTime dataEstorno;

    private String observacoes;
}

