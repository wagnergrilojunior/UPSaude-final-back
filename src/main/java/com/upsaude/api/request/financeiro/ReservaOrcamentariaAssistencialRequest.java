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
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Dados de reserva orçamentária assistencial")
public class ReservaOrcamentariaAssistencialRequest {

    @NotNull(message = "Competência financeira é obrigatória")
    private UUID competencia;

    private UUID agendamento;
    private UUID guiaAmbulatorial;
    private UUID documentoFaturamento;

    private UUID prestadorId;

    @Size(max = 30, message = "Tipo do prestador deve ter no máximo 30 caracteres")
    private String prestadorTipo; // ESTABELECIMENTO | PROFISSIONAL

    @NotNull(message = "Valor reservado total é obrigatório")
    @Positive(message = "Valor reservado total deve ser maior que zero")
    private BigDecimal valorReservadoTotal;

    @NotBlank(message = "Status é obrigatório")
    @Size(max = 20, message = "Status deve ter no máximo 20 caracteres")
    private String status; // ATIVA | CONSUMIDA | LIBERADA | PARCIAL

    private UUID grupoReserva;
}

